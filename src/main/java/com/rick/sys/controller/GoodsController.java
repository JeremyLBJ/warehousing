package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.GoodsVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.service.IBusGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping(value = "/goods")
@RestController
public class GoodsController {

    @Resource
    private IBusGoodsService goodsService;

    @RequestMapping(value = "/loadAllGoods",method = RequestMethod.GET)
    public DataGridView loadAllGoods (GoodsVO vo) {
        IPage<BusGoods> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("".equals(vo.getProviderid()),"providerid",vo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(vo.getGoodsname()),"goodsname",vo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(vo.getProductcode()),"productcode",vo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(vo.getPromitcode()),"promitcode",vo.getPromitcode());

        return null;
    }

}
