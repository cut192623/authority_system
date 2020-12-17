package service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import domain.Role;
import domain.User;
import domain.UserRole;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserRoleService;
import service.UserService;

import java.util.ArrayList;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void setRole(String id, ArrayList<Role> roles) {
        // 移除之前关联的用户角色数据
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        // 新增关联的用户角色数据
        for (Role role : roles) {
            UserRole userrole = new UserRole();
            userrole.setUserId(id);
            userrole.setRoleId(role.getId());
            userRoleService.save(userrole);
        }
    }
}
