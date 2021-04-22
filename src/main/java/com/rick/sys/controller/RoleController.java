package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.RoleVO;
import com.rick.sys.VO.RoleVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysRole;
import com.rick.sys.entity.SysRole;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysPermissionService;
import com.rick.sys.service.ISysRolePermissionService;
import com.rick.sys.service.ISysRoleService;
import com.rick.sys.untils.WebUntils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 */

@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysPermissionService sysPermissionService;

    @Resource
    private ISysRolePermissionService iSysRolePermissionService;


    /**
     * 根据多个组合条件查询
     * @param vo  接收前端数据
     * @return  返回对应数据
     */
    @RequestMapping(value = "/loadAllRole",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView queryAllRole (RoleVO vo) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        IPage<SysRole> page = new Page<>(vo.getPage(),vo.getLimit());
        wrapper.like(StringUtils.isNotBlank(vo.getName()),"name",vo.getName());
        wrapper.like(StringUtils.isNotBlank(vo.getRemark()),"remark",vo.getRemark());
        wrapper.ge(vo.getAvailable()!=null,"available",vo.getAvailable());
        wrapper.orderByDesc("createtime");
        this.sysRoleService.page(page,wrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }



    @RequestMapping(value = "/queryRoleById",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView queryRoleById (RoleVO vo) {
        SysRole byId = this.sysRoleService.getById(vo.getId());
        return new DataGridView(byId);
    }


    /**
     * 根据ID移除数据
     * @param vo  接收前端数据对象
     * @return  返回对应状态码
     */
    @RequestMapping(value = "/removeById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject removeById (RoleVO vo) {
        try {
            this.sysRoleService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }



    /**
     * 保存系统角色
     * @param vo
     * @return
     */
    @RequestMapping(value = "/saveRole",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRole (RoleVO vo) {
        try {
            vo.setCreatetime(LocalDateTime.now());
            vo.setAvailable(Constant.AVAILABLE);
            this.sysRoleService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        } catch(Exception e){
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }


    /**
     * 修改角色
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateRole (RoleVO vo) {
        try {
            vo.setCreatetime(LocalDateTime.now());
            this.sysRoleService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    @RequestMapping(value = "/initPermissionByRoleId",method = RequestMethod.POST)
    @ResponseBody
    public DataGridView initPermissionByRoleId ( Integer roleId) {
        // 查询所有可用的菜单和权限
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constant.AVAILABLE);
        List<SysPermission> list = this.sysPermissionService.list(queryWrapper);
        //根据角色ID查询此角色拥有那些权限和菜单ID
        List<Integer> idList = this.sysRoleService.queryIdsByRoleId(roleId);
        List<SysPermission> sysPermissionsList = null;
        if (idList.size() > 0) {
            queryWrapper.in("id",idList);
            sysPermissionsList = this.sysPermissionService.list(queryWrapper);
        }else {
            sysPermissionsList = new ArrayList<>();
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        //构建角色和菜单树
        for (SysPermission p1: list) {
            String checkArr = "0";
            for (SysPermission p2: sysPermissionsList) {
                if (p1.getId().equals(p2.getId())) {
                    checkArr = "1";
                }
            }
            treeNodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), checkArr));
        }
        return new DataGridView(treeNodes);
    }


    @RequestMapping(value = "/saveRolePermission",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveRolePermission (Integer rid , Integer [] ids) {
        try {
            this.iSysRolePermissionService.saveRolePermission(rid,ids);
            return ResultObject.DISPATCH_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DISPATCH_ERROR;
        }
    }
}
