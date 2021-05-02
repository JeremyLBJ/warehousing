package com.rick.sys.service.impl;

import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.BusInport;
import com.rick.sys.entity.BusOutport;
import com.rick.sys.entity.SysUser;
import com.rick.sys.mapper.BusGoodsMapper;
import com.rick.sys.mapper.BusInportMapper;
import com.rick.sys.mapper.BusOutportMapper;
import com.rick.sys.service.IBusGoodsService;
import com.rick.sys.service.IBusInportService;
import com.rick.sys.service.IBusOutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.sys.untils.WebUntils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rick
 * @since 2021-04-02
 */
@Service
@Transactional
public class BusOutportServiceImpl extends ServiceImpl<BusOutportMapper, BusOutport> implements IBusOutportService {

    @Resource
    private BusGoodsMapper goodsMapper;

    @Resource
    private BusInportMapper inportMapper;

    @Override
    public void addOutport(Integer id, Integer number, String remark) {
        // 通过ID查询进货订单信息
        BusInport inport = this.inportMapper.selectById(id);
        // 进货订单信息 查询商品信息
        BusGoods goods = this.goodsMapper.selectById(inport.getGoodsid());
        //修改商品信息
        goods.setNumber(goods.getNumber() - number);
        this.goodsMapper.updateById(goods);
        //添加到退货中
        SysUser u = (SysUser) WebUntils.getSession().getAttribute("user");
        BusOutport outPort = new BusOutport();
        outPort.setNumber(number);
        outPort.setGoodsid(goods.getId());
        outPort.setOutportprice((number * inport.getInportprice()));
        outPort.setOutputtime(LocalDateTime.now());
        outPort.setRemark(remark);
        outPort.setPaytype(inport.getPaytype());
        outPort.setOperateperson(u.getName());
        outPort.setProviderid(inport.getProviderid());
        this.getBaseMapper().insert(outPort);
    }
}
