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
import org.springframework.web.multipart.MultipartFile;
import service.UserRoleService;
import service.UserService;
import szx.ShiroUtils;
import szx.UUIDUtils;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserService userService;

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

    @Override
    public boolean addUser(User user, MultipartFile img) {

        user.setId(UUIDUtils.getID());
        user.setSalt(UUIDUtils.getID());
        user.setCreateTime(new Timestamp(new Date().getTime()));

        String newFileName = null;
        if (img!=null){
             newFileName = UUID.randomUUID() + img.getOriginalFilename();
            user.setUserImg(newFileName);
            //对用户密码加密
            ShiroUtils.encrypt(user);
            //新增用户
            boolean save = userService.save(user);
            File file = new File("/usr/local/nginx/html");
            try {
                img.transferTo(new File(file,newFileName));
                return save;
            }catch (Exception e){
                e.printStackTrace();
                return save;
            }
        }else {
            user.setUserImg(null);
            //对用户密码加密
            ShiroUtils.encrypt(user);
            //新增用户
            boolean save = userService.save(user);

            return save;
        }



    }
}
