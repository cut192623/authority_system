package mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import domain.Menu;
import domain.RoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
