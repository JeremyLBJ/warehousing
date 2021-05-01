package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.GoodsVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.BusProvider;
import com.rick.sys.service.IBusGoodsService;
import com.rick.sys.service.IBusProviderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.List;

@RequestMapping(value = "/goods")
@RestController
public class GoodsController {

    @Resource
    private IBusGoodsService goodsService;

    @Resource
    private IBusProviderService busProviderService;

    @RequestMapping(value = "/loadAllGoods",method = RequestMethod.GET)
    public DataGridView loadAllGoods (GoodsVO vo) {
        IPage<BusGoods> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("".equals(vo.getProviderid()),"providerid",vo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(vo.getGoodsname()),"goodsname",vo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(vo.getProductcode()),"productcode",vo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(vo.getPromitcode()),"promitcode",vo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(vo.getDescription()),"description",vo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(vo.getSize()),"size",vo.getSize());
        this.goodsService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping(value = "/loadAllProviderForSelect",method = RequestMethod.GET)
    public DataGridView loadAllProviderForSelect () {
        QueryWrapper<BusProvider> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE);
        List<BusProvider> list = this.busProviderService.list(queryWrapper);
        return new DataGridView(list);
    }


    @RequestMapping(value = "/addGoods",method = RequestMethod.POST)
    public ResultObject addGoods (GoodsVO vo) {
        try {
            this.goodsService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }


    @RequestMapping(value = "/updateGoods",method = RequestMethod.POST)
    public ResultObject updateGoods (GoodsVO vo) {
        try {
            this.goodsService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }


    @RequestMapping(value = "/loadAllGoodsForSelect",method = RequestMethod.GET)
    public DataGridView loadAllGoodsForSelect () {
        QueryWrapper<BusGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE);
        List<BusGoods> list = this.goodsService.list(queryWrapper);
        for (BusGoods goods:list) {
            BusProvider byId = this.busProviderService.getById(goods.getProviderid());
            if (null != byId) {
                goods.setProvidername(byId.getProvidername());
            }
        }
        return new DataGridView(list);
    }

    @RequestMapping(value = "/loadGoodsByProviderId",method = RequestMethod.GET)
    public DataGridView loadGoodsByProviderId (Integer providerid) {
        QueryWrapper<BusGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE);
        queryWrapper.eq(null != providerid && 0 != providerid,"providerid",providerid);
        List<BusGoods> list = this.goodsService.list(queryWrapper);
        for (BusGoods goods:list) {
            BusProvider byId = this.busProviderService.getById(goods.getProviderid());
            if (null != byId) {
                goods.setProvidername(byId.getProvidername());
            }
        }
        return new DataGridView(list);
    }

}
