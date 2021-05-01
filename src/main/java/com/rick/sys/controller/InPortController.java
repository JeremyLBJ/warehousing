package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.GoodsVO;
import com.rick.sys.VO.InPortVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.BusInport;
import com.rick.sys.entity.BusProvider;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.IBusGoodsService;
import com.rick.sys.service.IBusInportService;
import com.rick.sys.service.IBusProviderService;
import com.rick.sys.untils.WebUntils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(value = "/inport")
@RestController
public class InPortController {

    @Resource
    private IBusGoodsService goodsService;

    @Resource
    private IBusProviderService busProviderService;

    @Resource
    private IBusInportService inportService;

    @Resource
    private IBusProviderService providerService;

    @RequestMapping(value = "/loadAllInport",method = RequestMethod.GET)
    public DataGridView loadAllInport (InPortVO vo) {
        IPage<BusInport> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusInport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( null != vo.getProviderid() && 0 != vo.getProviderid(),"providerid",vo.getProviderid());
        queryWrapper.eq(null != vo.getGoodsid() && 0 != vo.getGoodsid(),"goodsid",vo.getGoodsid());
        queryWrapper.like(StringUtils.isNotBlank(vo.getOperateperson()),"operateperson",vo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(vo.getRemark()),"remark",vo.getRemark());
        queryWrapper.ge(vo.getStartTime()!=null,"inporttime",vo.getStartTime());
        queryWrapper.le(vo.getEndTime()!=null,"inporttime",vo.getEndTime());
        queryWrapper.orderByDesc("inporttime");
        this.inportService.page(page,queryWrapper);
        List<BusInport> records = page.getRecords();
        for (BusInport bus: records) {
            BusProvider provider = this.providerService.getById(bus.getProviderid());
            if (null != provider) {
                bus.setProvidername(provider.getProvidername());
            }
            BusGoods goods = this.goodsService.getById(bus.getGoodsid());
            if (null != goods){
                bus.setGoodsname(goods.getGoodsname());
                bus.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(),records);
    }


    @RequestMapping(value = "/loadAllProviderForSelect",method = RequestMethod.GET)
    public DataGridView loadAllProviderForSelect () {
        QueryWrapper<BusProvider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE);
        List<BusProvider> list = this.busProviderService.list(queryWrapper);
        return new DataGridView(list);
    }


    @RequestMapping(value = "/addInport",method = RequestMethod.POST)
    public ResultObject addInport (InPortVO vo) {
        try {
            vo.setInporttime(LocalDateTime.now());
            SysUser u = (SysUser) WebUntils.getSession().getAttribute("user");
            vo.setOperateperson(u.getName());
            this.inportService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }


    @RequestMapping(value = "/updateInport",method = RequestMethod.POST)
    public ResultObject updateInport (InPortVO vo) {
        try {
            vo.setInporttime(LocalDateTime.now());
            SysUser u = (SysUser) WebUntils.getSession().getAttribute("user");
            vo.setOperateperson(u.getName());
            this.inportService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }

    @RequestMapping(value = "/deleteInport",method = RequestMethod.POST)
    public ResultObject deleteInport (InPortVO vo) {
        try {
            this.inportService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }
}
