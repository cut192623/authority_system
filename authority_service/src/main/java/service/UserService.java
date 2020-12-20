package service;

import com.baomidou.mybatisplus.extension.service.IService;
import domain.Role;
import domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface UserService extends IService<User> {

    void setRole(String id, ArrayList<Role> roles);
    boolean addUser(User user, MultipartFile img);
}
