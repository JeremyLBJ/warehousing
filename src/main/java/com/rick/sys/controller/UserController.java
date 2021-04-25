package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.UserVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.entity.SysDept;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysDeptService;
import com.rick.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Rick
 */

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysDeptService iSysDeptService;


    @RequestMapping(value = "/loadAllUsers",method = RequestMethod.GET)
    public DataGridView loadAllUsers(UserVO vo) {
        IPage<SysUser> page = new Page<>(vo.getPage(),vo.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(vo.getName()),"name",vo.getName());
        queryWrapper.like(StringUtils.isNotBlank(vo.getAddress()),"address", vo.getAddress());
        queryWrapper.eq("type", Constant.AVERAGE_USER);
        queryWrapper.eq(null != vo.getDeptid(),"deptid",vo.getDeptid());
        this.iSysUserService.page(page,queryWrapper);
        List<SysUser> records = page.getRecords();
        for (SysUser user: records){
            if (null != user.getDeptid()) {
                QueryWrapper<SysDept> deptQueryWrapper = new QueryWrapper<>();
                deptQueryWrapper.eq("id",user.getDeptid());
                SysDept one = this.iSysDeptService.getOne(deptQueryWrapper);
                user.setDeptName(one.getTitle());
            }
            if (null != user.getMgr()) {
                QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("id",user.getMgr());
                SysUser one = this.iSysUserService.getOne(userQueryWrapper);
                user.setLeaderName(one.getName());
            }
        }
        return new DataGridView(page.getTotal(),records);
    }
}
