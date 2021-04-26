package com.rick.sys.service.impl;

import com.rick.sys.entity.SysRoleUser;
import com.rick.sys.mapper.SysRoleUserMapper;
import com.rick.sys.service.ISysRoleUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements ISysRoleUserService {

    @Override
    public List<Integer> queryRidByUid(Integer id) {
        List<Integer> integers = this.getBaseMapper().queryRidByUid(id);
        return integers;
    }
}
