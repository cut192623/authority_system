package service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import domain.Menu;
import domain.RoleMenu;
import mapper.RoleMenuMapper;
import mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RoleMenuService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public void setRoleMenu(String roleId, ArrayList<Menu> menuIds) {

        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));

        menuIds.forEach(menu->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menu.getId());
            roleMenuService.save(roleMenu);
        });


    }
}
