package service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import domain.UserRole;
import mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
