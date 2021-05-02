package com.rick.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.mapper.BusGoodsMapper;
import com.rick.sys.service.IBusGoodsService;
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
public class BusGoodsServiceImpl extends ServiceImpl<BusGoodsMapper, BusGoods> implements IBusGoodsService {

    @Override
    public boolean save(BusGoods entity) {
        return super.save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(BusGoods entity) {
        return super.updateById(entity);
    }

    @Override
    public BusGoods getOne(Wrapper<BusGoods> queryWrapper) {
        return super.getOne(queryWrapper);
    }
}
