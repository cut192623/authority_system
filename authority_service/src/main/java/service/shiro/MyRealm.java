package service.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import domain.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import service.*;

import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo Info = new SimpleAuthorizationInfo();
//        获取当前用户
        User user = (User) principalCollection.getPrimaryPrincipal();
//        获取当前用户拥有的角色的集合
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", user.getId()));

        if (userRoleList.size()>0){//如果当前用户有被分配角色
            for (UserRole userRole : userRoleList) {
                //获取list里的每个role的roleId
                String roleId = userRole.getRoleId();
                //通过roleId查询
                List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
                if (null!=roleMenuList){//如果当前角色有被分配权限
                    for (RoleMenu roleMenu : roleMenuList) {
                        //获取当前menuId
                        String menuId = roleMenu.getMenuId();
                        List<Menu> menuList = menuService.list(new QueryWrapper<Menu>().eq("id", menuId));
                        if (null!=menuList){
                            for (Menu menu : menuList) {
                                Info.addStringPermission(menu.getPermiss());
                            }
                        }
                    }
                }

            }
        }
        return Info;


    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.getOne(new QueryWrapper<User>().eq("user_name", token.getUsername()));
        if (user == null) {
            throw new UnknownAccountException("用户名或密码有误");
        }
        SimpleByteSource byteSource = new SimpleByteSource(token.getUsername() + user.getSalt());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), byteSource, getName());
        return simpleAuthenticationInfo;
    }

  
}
