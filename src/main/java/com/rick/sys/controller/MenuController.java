package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rick.sys.VO.PermissionVO;
import com.rick.sys.common.BuilderTreeNode;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysPermissionService;
import com.rick.sys.untils.WebUntils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
}
