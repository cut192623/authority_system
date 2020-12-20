package szx.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import domain.Role;
import domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import szx.JsonResult.*;
import szx.ShiroUtils;
import szx.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    static String fileName = "";
    @Autowired
    private UserService userService;



    /**
     * 用户列表跳转
     * @return
     */

    @RequestMapping("/toListPage")
    @RequiresPermissions("user:list")
    public String toListPage() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal().toString());
        return "admin/user/user_list";
    }

    /**
     * 用户分页
     * @param page
     * @param limit
     * @param userName
     * @param tel
     * @param email
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public IPage<User> userList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "5") Integer limit,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "tel", required = false) String tel,
            @RequestParam(value = "email", required = false) String email,
            HttpServletRequest request) {
        Page<User> Data = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (null != userName) wrapper.like("user_name", userName);
        if (null != tel) wrapper.like("tel", tel);
        if (null != email) wrapper.like("email", email);


        return userService.page(Data, wrapper);
    }

    /**
     * 用户添加页面跳转
     * @return
     */

    @RequestMapping("/toAddPage")
    @RequiresPermissions("user:add")
    public String toAddPage() {
        return "admin/user/user_add";
    }

    /**
     * 用户名查重
     * @param username
     * @return
     */
    @RequestMapping("/checkUserName")
    @ResponseBody
    public JsonResult checkUserName(String username) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        User one = userService.getOne(wrapper.eq("user_name", username));
        if (one != null)
            return new JsonResult(400, "用户已存在", null);
        else
            return new JsonResult(200, "success", null);

    }

    /**
     * 新增用户
     * */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(

                            @RequestParam("user") String user1
                            ,@RequestParam(value = "img",required = false) MultipartFile file
    ) {
        User user = JSONObject.parseObject(user1,User.class);


        boolean b = userService.addUser(user, file);
        if (b) {
            return new JsonResult(200,"success",null);
        }
        else {
            return new JsonResult(400, "failed", null);
        }
    }

    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(String id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin/user/user_update";
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(User user) {
        ShiroUtils.encrypt(user);
        user.setUpdateTime(new Timestamp(new Date().getTime()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        boolean update = userService.update(user, queryWrapper.eq("id", user.getId()));
        if (update)
            return new JsonResult(200, "success", null);
        else
            return new JsonResult(400, "failed", null);
    }

    /**
     * 删除（支持批量删除）
     */


    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(@RequestBody ArrayList<User> users) {
        try {
            List<String> list = new ArrayList<String>();

            for (User user : users) {
                if ("root".equals(user.getUserName())) {
                    throw new Exception("root账号不能被删除");
                }
                list.add(user.getId());
            }
            userService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(400, e.getMessage());
        }
        return new JsonResult(200, "success");
    }

    @RequestMapping("/toSetRolePage")
    public String setRole(String id, Model model) {
        model.addAttribute("user", id);
        return "admin/user/user_setRole";
    }

    /** 设置角色 */
    @ResponseBody
    @RequestMapping("setRole")
    public JsonResult setRole(String id, @RequestBody ArrayList<Role> roles) {
        userService.setRole(id, roles);
        return new JsonResult(200,"success",null);
    }


}

