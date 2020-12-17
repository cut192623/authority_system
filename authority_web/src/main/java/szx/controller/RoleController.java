package szx.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import domain.Role;
import domain.User;
import domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.RoleService;
import service.UserRoleService;
import szx.JsonResult.JsonResult;
import szx.PageEntity;
import szx.UUIDUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("toListPage")
    public String toListPage() {
        return "admin/role/role_list";
    }

    @RequestMapping("list")
    @ResponseBody
    public IPage<Role> list(@RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                            @RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                            @RequestParam(value = "role", required = false) String role) {
        Page<Role> Data = new Page<>(page, limit);
        if (role != null && role != "") {

            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("role", role);
            return roleService.page(Data, queryWrapper);

        }
        return roleService.page(Data);
    }

    @RequestMapping("toAddPage")
    public String toAddPage() {
        return "admin/role/role_add";
    }

    @RequestMapping("add")
    @ResponseBody
    public JsonResult add(Role role) {
        role.setId(UUIDUtils.getID());
        role.setCreateTime(new Timestamp(new Date().getTime()));
        boolean save = roleService.save(role);
        if (save)
            return new JsonResult(200, "success", null);
        else
            return new JsonResult(400, "failed", null);
    }

    @RequestMapping("toUpdatePage")
    public String toUpdatePage(String id, Model model) {
        Role role = roleService.getById(id);
        model.addAttribute("role", role);
        return "admin/role/role_update";
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult update(Role role) {
        role.setUpdateTime(new Timestamp(new Date().getTime()));
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        boolean update = roleService.update(role, queryWrapper.eq("id", role.getId()));
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
    public JsonResult delete(@RequestBody ArrayList<Role> roles) {
        try {
            List<String> list = new ArrayList<>();
            for (Role role : roles) {
                list.add(role.getId());
            }
            roleService.removeByIds(list);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(400, e.getMessage());
        }
        return new JsonResult(200, "success");
    }


    @RequestMapping("toSetMenuPage")
    public String roleSetMenu(String id, Model model) {
        model.addAttribute("role_id", id);
        System.out.println(id);
        return "admin/role/role_setMenu";
    }

    /*** 查询用户关联的角色列表 */
    @ResponseBody
    @RequestMapping("/roleList")
    public PageEntity List(String userId, Role role) {
        List<UserRole> userRoles = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        if (role != null) {
            if (!StringUtils.isEmpty(role.getRole())) queryWrapper.like("role", role.getRole());
        }
        List<Role> roles = roleService.list(queryWrapper);
        List<JSONObject> list = new ArrayList<>();
        // 同样需要对用户已经关联的角色进行勾选，根据layui需要填充一个LAY_CHECKED字段
        for (Role role2 : roles) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(role2));
            boolean rs = false;
            for (UserRole userRole : userRoles) {
                if (userRole.getRoleId().equals(role2.getId())) {
                    rs = true;
                }
            }
            jsonObject.put("LAY_CHECKED", rs);
            list.add(jsonObject);
        }
        return new PageEntity(list.size(), list);
    }




}
