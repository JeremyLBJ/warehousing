package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.PerviderVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.BusProvider;
import com.rick.sys.entity.BusProvider;
import com.rick.sys.service.IBusProviderService;
import com.rick.sys.service.IBusProviderService;
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
@RequestMapping(value = "/provider")
public class PerviderController {
    
    @Resource
    private IBusProviderService busProviderService ;


    @RequestMapping(value = "/loadAllProvider",method = RequestMethod.GET)
    public DataGridView loadAllProvider (PerviderVO vo) {
        IPage<BusProvider> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<BusProvider> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getProvidername()),"providername",vo.getProvidername());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getPhone()),"phone",vo.getPhone());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getConnectionperson()),"connectionperson",vo.getConnectionperson());
        this.busProviderService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping(value = "/addProvider",method = RequestMethod.POST)
    public ResultObject addProvider (PerviderVO vo) {
        try {
            this.busProviderService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }

    @RequestMapping(value = "/updateProvider",method = RequestMethod.POST)
    public ResultObject updateProvider ( PerviderVO vo) {
        try {
            this.busProviderService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }

    @RequestMapping(value = "/deleteProvider",method = RequestMethod.POST)
    public ResultObject deleteProvider (PerviderVO vo) {
        try {
            this.busProviderService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    @RequestMapping(value = "/batchDeleteProvider",method = RequestMethod.POST)
    public ResultObject batchDeleteProvider (Integer[] ids) {
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer in: ids) {
                idList.add(in);
            }
            this.busProviderService.removeByIds(idList);
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }

}
