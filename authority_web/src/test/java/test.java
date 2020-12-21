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
import org.springframework.web.multipart.MultipartFile;
import service.RoleMenuService;
import service.UserService;
import service.shiro.MyRealm;
import szx.OSSUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Test
    public void test1() throws Exception{
        OSSUtils ossUtils = new OSSUtils();
        String s = "C:\\Users\\邵\\Pictures\\Camera Roll\\WIN_20201221_01_16_30_Pro.jpg";

    }


}
