package szx.controller;


import domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/check")
public class LoginController {

    /**
     * 登录跳转页面
     * @param user
     * @param model
     * @return
     */
    @PostMapping (value = "login" )
    public String login(User user, Model model){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            model.addAttribute("user",subject.getPrincipal());
            return "redirect:/check/toIndexPage";
        }catch (Exception e){
            model.addAttribute("msg","账户"+token.getUsername()+"的用户名或密码错误");
            return "forward:/login.jsp";
        }
    }
    @RequestMapping("toIndexPage")
    public String toIndexPage(){
        return "admin/index";
    }


    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        return "forward:/login.jsp";
    }
}
