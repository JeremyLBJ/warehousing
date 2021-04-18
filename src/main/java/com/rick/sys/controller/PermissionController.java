package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.PermissionVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.service.ISysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rick
 * 权限前端控制器
 */
@RequestMapping("/permission")
@Controller
public class PermissionController {

    @Resource
    private ISysPermissionService sysPermissionService;


    @RequestMapping(value = "/loadAllPermission",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView loadAllPermission (PermissionVO vo) {
        IPage<SysPermission> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.eq("type",Constant.PERMISSION);
        this.sysPermissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }


    @RequestMapping(value = "/loadManagerLeftTreeJson",method = RequestMethod.POST)
    @ResponseBody
    public DataGridView loadManagerLeftTreeJson (PermissionVO vo) {
        QueryWrapper<SysPermission> query = new QueryWrapper<>();
        query.eq("type", Constant.MENU);
        query.eq("available",Constant.AVAILABLE);
        List<SysPermission> list = this.sysPermissionService.list(query);
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysPermission p: list) {
            Integer pid = p.getPid();
            Integer id = p.getId();
            String title = p.getTitle();
            Boolean spread = p.getOpen()==1?true:false;
            treeNodeList.add(new TreeNode( id,  pid,  title,  spread));
        }
        return new DataGridView(treeNodeList);
    }


    /**
     * 加载最大的排序码
     * @param
     * @return
     */
    @RequestMapping(value = "/loadPermissionMaxOrderNum",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loadPermissionMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",Constant.PERMISSION);
        queryWrapper.orderByDesc("ordernum");
        List<SysPermission> list = this.sysPermissionService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }


    /**
     * 添加
     * @param vo
     * @return
     */
    @RequestMapping(value = "/savePermission",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject savePermission (@Valid PermissionVO vo) {
        try {
            vo.setType(Constant.PERMISSION);
            this.sysPermissionService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }

    /**
     * 移除
     * @param vo
     * @return
     */
    @RequestMapping(value = "/removeById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject removeById (PermissionVO vo) {
        try {
            this.sysPermissionService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }

    /**
     * 修改
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updatePermissionById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updatePermissionById (PermissionVO vo) {
        try {
            this.sysPermissionService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }


}
