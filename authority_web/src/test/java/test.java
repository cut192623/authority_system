import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import domain.RoleMenu;
import domain.User;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import service.RoleMenuService;
import service.UserService;
import service.shiro.MyRealm;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuService roleMenuService;

    public static void main(String[] args) {

    }


}
