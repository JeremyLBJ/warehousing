package com.rick.sys.controller;

import com.rick.sys.VO.DeptVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysDept;
import com.rick.sys.service.ISysDeptService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private ISysDeptService iSysDeptService;


    @RequestMapping(value = "/loadDeptManagerLeftTreeJson",method = RequestMethod.POST)
    @ResponseBody
    public DataGridView loadDeptManagerLeftTreeJson (DeptVO vo) {
        List<SysDept> list = this.iSysDeptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (SysDept dept: list) {
            Boolean spread = dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }
}
