package szx.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import domain.Menu;
import domain.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.MenuService;
import service.RoleMenuService;
import szx.IconFontUtils;
import szx.JsonResult.JsonResult;
import szx.PageEntity;
import szx.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @RequestMapping("toListPage")
    public String toListPage() {
        return "admin/menu/menu_list";
    }

    @RequestMapping("list")
    @ResponseBody
    public PageEntity list(@RequestParam(value = "id", required = false) String id) {
        List<Menu> pList = menuService.list(new QueryWrapper<Menu>().eq("pid", "0"));
        //所有权限
        ArrayList<Menu> menus = new ArrayList<>();
        //用户选择设置的权限
        ArrayList<String> ids = new ArrayList<>();
        //所有权限的json格式
        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        findChildMenu(pList, menus);

        if (id != null && id.length() > 0) {
            //查询所选角色拥有的权限
            List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", id));
            roleMenuList.forEach(item -> ids.add(item.getMenuId()));

            menus.forEach(menu -> {
                        // 先需要把对象转换为JSON格式因为在对某个角色添加权限的时候，该角色可能已经有了一些权限，
                        // 所以需要查询角色已有的权限在页面进行判断勾选中，此处根据layui框架table模块的说明，需要返回一个LAY_CHECKED字段。
                        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(menu));
                        // 判断是否已经有了对应的权限
                        if (ids.contains(menu.getId())) {
                            jsonObject.put("LAY_CHECKED", true);
                        }
                        jsonObjects.add(jsonObject);
                    });
            return new PageEntity(jsonObjects.size(),jsonObjects);
        }
        return new PageEntity(menus.size(), menus);
    }

    private List<Menu> findChildMenu(List<Menu> pList, List<Menu> menus) {
        for (Menu menu : pList) {
            if (!menus.contains(menu)) {
                menus.add(menu);
            }
            String pId = menu.getId();
            List<Menu> childList = menuService.list(new QueryWrapper<Menu>().eq("pid", pId));
            menu.setNodes(childList);

            if (childList.size() > 0) {
                menus = findChildMenu(childList, menus);
            }
        }
        return menus;
    }

    @RequestMapping("/addPage")
    public String toAddPage(HttpServletRequest request) {
        List<Menu> pMenus = menuService.list(new QueryWrapper<Menu>().eq("pid", "0"));
        pMenus.forEach(menu -> {
            List<Menu> childMenus = menuService.list(new QueryWrapper<Menu>().eq("pid", menu.getId()));
            menu.setNodes(childMenus);
        });

        // 2、查询所有字体图标(查询所有字体图片class类)
        List<String> iconFont = IconFontUtils.getIconFont();

        request.setAttribute("list", pMenus);
        request.setAttribute("iconFont", iconFont);

        return "admin/menu/menu_add";
    }

    @ResponseBody
    @RequestMapping("/add")
    public JsonResult addMenu(Menu menu) {
        menu.setId(UUIDUtils.getID());
        menu.setCreateTime(new Timestamp(new Date().getTime()));
        menuService.save(menu);
        return new JsonResult(200, "success", null);
    }

    @ResponseBody
    @RequestMapping("delete")
    public JsonResult delete(String id) {
        boolean b = menuService.removeById(id);
        if (b)
            return new JsonResult(200, "success", null);
        else
            return new JsonResult(400, "failed", null);
    }

    @RequestMapping("roleSetMenu")
    @ResponseBody
    public JsonResult test(String roleId, @RequestBody ArrayList<Menu> menus) {

        roleMenuService.setRoleMenu(roleId, menus);
        return new JsonResult(200, "success", null);

    }
}
