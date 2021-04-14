package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.DeptVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysDept;
import com.rick.sys.service.ISysDeptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Rick
 * 部门前端控制器
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private ISysDeptService iSysDeptService;


    /**
     * 加载部门左侧树型结构
     * @param vo
     * @return
     */
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


    /**
     * 全查询数据
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadAllDept",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView loadAllDept (DeptVO vo) {
        IPage<SysDept> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(vo.getTitle()),"title",vo.getTitle());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getRemark()),"remark",vo.getTitle());
        queryWrapper.like(StringUtils.isNoneBlank(vo.getAddress()),"address",vo.getTitle());
        queryWrapper.eq(vo.getId()!=null,"id",vo.getId()).or().eq(vo.getId()!=null,"pid",vo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.iSysDeptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 根据id移除
     * @param vo
     * @return
     */
    @RequestMapping(value = "/removeById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject removeById (DeptVO vo) {
        try{
            this.iSysDeptService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    /**
     * 添加部门
     * @param vo
     * @return
     */
    @RequestMapping(value = "/saveDept",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject save (DeptVO vo) {
        try{
            vo.setCreatetime(LocalDateTime.now());
            this.iSysDeptService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_SUCCESS;
        }
    }


    /**
     * 根据id修改部门信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateDeptById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateById (DeptVO vo) {
        try {
            vo.setCreatetime(LocalDateTime.now());
            this.iSysDeptService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }
}
