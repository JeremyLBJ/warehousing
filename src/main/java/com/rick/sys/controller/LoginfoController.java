package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.LogInfoVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.SysLogLogin;
import com.rick.sys.service.ISysLogLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author Rick
 * 登录日志前端控制器
 */
@Controller
@RequestMapping("/logInfo")
public class LoginfoController {

    @Resource
    private ISysLogLoginService iSysLogLoginService;


    /**
     * 登录日志全查询
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadAllLogInfo",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView loadAllLogInfo (LogInfoVO vo) {
        IPage<SysLogLogin> page = new Page(vo.getPage(),vo.getLimit());
        QueryWrapper<SysLogLogin> queryWapper = new QueryWrapper<>();
        queryWapper.like(StringUtils.isNotBlank(vo.getLoginname()),"loginname",vo.getLoginname());
        queryWapper.like(StringUtils.isNotBlank(vo.getLoginip()),"loginip",vo.getLoginip());
        queryWapper.ge(vo.getStartTime()!=null,"logintime",vo.getStartTime());
        queryWapper.le(vo.getEndTime()!=null,"logintime",vo.getEndTime());
        queryWapper.orderByDesc("logintime");
        this.iSysLogLoginService.page(page,queryWapper);
        return  new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 根据ID进行移除
     * @param vo
     * @return
     */
    @RequestMapping(value = "/deleteLogInfoById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject deleteLogInfoById (LogInfoVO vo) {
        try {
            this.iSysLogLoginService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    /**
     * 批量删除
     * @param vo
     * @return
     */
    @RequestMapping(value = "/deleteLogInfoByIds",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject deleteLogInfoByIds (LogInfoVO vo){
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer id: vo.getIds()) {
                idList.add(id);
            }
            this.iSysLogLoginService.removeByIds(idList);
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }

    }

}
