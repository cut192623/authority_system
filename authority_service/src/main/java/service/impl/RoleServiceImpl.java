package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import domain.Role;
import mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
