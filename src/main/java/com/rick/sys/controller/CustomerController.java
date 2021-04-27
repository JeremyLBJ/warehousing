package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.CustomerVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.BusCustomer;
import com.rick.sys.entity.SysNotice;
import com.rick.sys.service.IBusCustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Resource
    private IBusCustomerService busCustomerService;


    @RequestMapping(value = "/loadAllCustomer",method = RequestMethod.GET)
    public DataGridView loadAllCustomer (CustomerVO vo) {
        IPage<BusCustomer> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getCustomername()),"customername",vo.getCustomername());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getPhone()),"phone",vo.getPhone());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getConnectionperson()),"connectionperson",vo.getConnectionperson());
        this.busCustomerService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping(value = "/addCustomer",method = RequestMethod.POST)
    public ResultObject addCustomer (@Valid CustomerVO vo) {
        try {
            this.busCustomerService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }

    @RequestMapping(value = "/updateCustomer",method = RequestMethod.POST)
    public ResultObject updateCustomer (@Valid CustomerVO vo) {
        try {
            this.busCustomerService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }

    @RequestMapping(value = "/deleteCustomer",method = RequestMethod.POST)
    public ResultObject deleteCustomer (CustomerVO vo) {
        try {
            this.busCustomerService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    @RequestMapping(value = "/batchDeleteCustomer",method = RequestMethod.POST)
    public ResultObject batchDeleteCustomer (Integer[] ids) {
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer in: ids) {
                idList.add(in);
            }
            this.busCustomerService.removeByIds(idList);
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }

}
