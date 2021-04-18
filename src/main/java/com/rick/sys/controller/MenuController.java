package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.MenuVO;
import com.rick.sys.VO.PermissionVO;
import com.rick.sys.common.*;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysPermissionService;
import com.rick.sys.untils.WebUntils;
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
 * 菜单前端控制器
 * @author Rick
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private ISysPermissionService sysPermissionService;


    @RequestMapping(value = "indexMenuJson",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView indexMenuJson (PermissionVO vo) {
        System.out.println("进入");
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.MENU);
        queryWrapper.eq("available",Constant.AVAILABLE);
        SysUser user = (SysUser) WebUntils.getSession().getAttribute("user");
        List<SysPermission> list = null;
        if (Constant.SUPER_USER.equals(user.getType())) {
            // 超级管理员
            list = this.sysPermissionService.list(queryWrapper);
        }else {
            // 普通管理者
            list = this.sysPermissionService.list(queryWrapper);
        }
        List<TreeNode> trees = new ArrayList<>();
        for (SysPermission s: list) {
            Integer id = s.getId();
            Integer pid = s.getPid();
            String title = s.getTitle();
            String icon = s.getIcon();
            String href = s.getHref();
            Boolean spread = s.getOpen().equals(Constant.IS_OPEN) ?true:false;
            trees.add(new TreeNode( id,  pid,  title,  icon,  href,  spread));
        }
        //构建层级关系
        List<TreeNode> treeNodes = BuilderTreeNode.builderTree(trees, 1);
        return new DataGridView(treeNodes);
    }


    @RequestMapping(value = "/loadMenuManagerLeftTreeJson",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView loadMenuManagerLeftTreeJson (PermissionVO vo) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.MENU);
        queryWrapper.eq("available",Constant.AVAILABLE);
        List<SysPermission> list = this.sysPermissionService.list(queryWrapper);
        return null;
    }


    /**
     * 全查询
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadAllMenu",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView loadAllMenu (PermissionVO vo) {
        IPage<SysPermission> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.MENU);
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.eq(vo.getId() != null,"id",vo.getId()).or().eq(vo.getId()!= null,"pid",vo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.sysPermissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }



    /**
     * 加载最大的排序码
     * @param
     * @return
     */
    @RequestMapping(value = "/loadMenuMaxOrderNum",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();

        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",Constant.MENU);
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
    @RequestMapping(value = "/saveMenu",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveMenu (@Valid PermissionVO vo) {
        try {
            vo.setType(Constant.MENU);
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
    @RequestMapping(value = "/updateMenuById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateMenu (PermissionVO vo) {
        try {
            this.sysPermissionService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }


    /**
     * 查询当前节点是否有子节点
     * @param vo
     * @return
     */
    @RequestMapping(value = "/checkMenuChildren",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkMenuChildren (MenuVO vo) {
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid",vo.getId());
        List<SysPermission> list = this.sysPermissionService.list(queryWrapper);
        if (list.size()>0) {
            map.put("Value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }
    
    
    @RequestMapping(value = "/loadManagerLeftTreeJson",method = RequestMethod.POST)
    @ResponseBody
    public DataGridView loadManagerLeftTreeJson () {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.MENU);
        queryWrapper.eq("available",Constant.AVAILABLE);
        List<SysPermission> list = this.sysPermissionService.list(queryWrapper);
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysPermission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            Boolean spread = p.getOpen()==1?true:false;
            treeNodeList.add(new TreeNode( id,  pid,  title,  spread));
        }
        return new DataGridView(treeNodeList);
    }
}
