package szx.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/check")
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     * 登录跳转页面
     * @param user
     * @param request
     * @return
     */
    @PostMapping (value = "login" )
    public String login(User user, HttpServletRequest request, HttpSession session){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //session.setAttribute("user",user.getUserName());
            return "redirect:/check/toIndexPage?name="+user.getUserName();
        }catch (Exception e){
            request.setAttribute("msg","账户"+token.getUsername()+"的用户名或密码错误");
            return "forward:/login.jsp";
        }
    }
    @RequestMapping("toIndexPage")
    public String toIndexPage(String name,HttpServletRequest request){
        if (name!=null&&name!=""){
            User user = userService.getOne(new QueryWrapper<User>().eq("user_name", name));
            request.setAttribute("user",user);
        }
        return "admin/index";
    }


    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        return "forward:/login.jsp";
    }
}
