package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import domain.Menu;
import mapper.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.MenuService;

@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
