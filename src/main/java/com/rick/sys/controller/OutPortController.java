package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.OutPortVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.BusGoods;
import com.rick.sys.entity.BusOutport;
import com.rick.sys.entity.BusProvider;
import com.rick.sys.entity.SysNotice;
import com.rick.sys.service.IBusGoodsService;
import com.rick.sys.service.IBusOutportService;
import com.rick.sys.service.IBusProviderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping(value = "/outport")
@RestController
public class OutPortController {

    @Resource
    private IBusOutportService outportService;

    @Resource
    private IBusGoodsService goodsService;

    @Resource
    private IBusProviderService providerService;

    @RequestMapping(value = "/addOutport",method = RequestMethod.POST)
    public ResultObject addOutport (Integer id, Integer number,String remark) {
        try {
            this.outportService.addOutport(id,number,remark);
            return ResultObject.RETURNGOODS_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.RETURNGOODS_ERROR;
        }
    }


    @RequestMapping(value = "/loadAllOutport",method = RequestMethod.GET)
    public DataGridView loadAllOutport (OutPortVO vo) {
        IPage<BusOutport> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusOutport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(vo.getProviderid() != null && 0 != vo.getProviderid(),"providerid",vo.getProviderid());
        queryWrapper.eq(vo.getGoodsid() != null && 0 != vo.getGoodsid() ,"goodsid",vo.getGoodsid());
        queryWrapper.ge(vo.getStartTime()!=null,"outputtime",vo.getStartTime());
        queryWrapper.le(vo.getEndTime()!=null,"outputtime",vo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(vo.getOperateperson()),"operateperson",vo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(vo.getRemark()),"remark",vo.getRemark());
        this.outportService.page(page,queryWrapper);
        List<BusOutport> records = page.getRecords();
        for (BusOutport outPort: records) {
            BusProvider provider = this.providerService.getById(outPort.getProviderid());
            if (null != provider) {
                outPort.setProvidername(provider.getProvidername());
            }
            BusGoods goods = this.goodsService.getById(outPort.getGoodsid());
            if (null != goods) {
                outPort.setGoodsname(goods.getGoodsname());
                outPort.setSize(goods.getSize());
            }
        }
        System.out.println(records);
        return new DataGridView(page.getTotal(),records);
    }
}
