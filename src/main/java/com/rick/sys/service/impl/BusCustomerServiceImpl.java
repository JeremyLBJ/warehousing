package com.rick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.rick.sys.entity.BusCustomer;
import com.rick.sys.mapper.BusCustomerMapper;
import com.rick.sys.service.IBusCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
public class BusCustomerServiceImpl extends ServiceImpl<BusCustomerMapper, BusCustomer> implements IBusCustomerService {

    @Override
    public boolean save(BusCustomer entity) {
        return super.save(entity);
    }

    @Override
    public BusCustomer getOne(Wrapper<BusCustomer> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public boolean updateById(BusCustomer entity) {
        return super.updateById(entity);
    }
}
