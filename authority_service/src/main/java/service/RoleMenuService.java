package service;

import com.baomidou.mybatisplus.extension.service.IService;
import domain.Menu;
import domain.RoleMenu;

import java.util.ArrayList;

public interface RoleMenuService extends IService<RoleMenu> {
    void setRoleMenu(String roleId, ArrayList<Menu>menuIds);

}
