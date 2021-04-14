package com.rick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.rick.sys.entity.SysDept;
import com.rick.sys.mapper.SysDeptMapper;
import com.rick.sys.service.ISysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public SysDept getOne(Wrapper<SysDept> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean update(Wrapper<SysDept> updateWrapper) {
        return super.update(updateWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
