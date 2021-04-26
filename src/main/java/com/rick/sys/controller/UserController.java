package com.rick.sys.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.RoleVO;
import com.rick.sys.VO.UserVO;
import com.rick.sys.common.Constant;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.PinyinUtils;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.SysDept;
import com.rick.sys.entity.SysRole;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysDeptService;
import com.rick.sys.service.ISysRoleService;
import com.rick.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ISysRoleService sysRoleService;


    /**
     * 用户表查询
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadAllUser",method = RequestMethod.GET)
    public DataGridView loadAllUser(UserVO vo) {
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


    /**
     * 保存用户对象
     * @param vo
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResultObject save (UserVO vo) {
        try {
            vo.setHiredate(LocalDateTime.now());
            vo.setType(Constant.AVERAGE_USER);
            String salt= IdUtil.simpleUUID().toUpperCase();
            //设置盐
            vo.setSalt(salt);
            //设置密码
            vo.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());
            this.iSysUserService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }


    /**
     * 根据ID移除对象
     * @param vo
     * @return
     */
    @RequestMapping(value = "/removeById",method = RequestMethod.POST)
    public ResultObject removeById (UserVO vo) {
        try {
            this.iSysUserService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    /**
     * 加载最大的排序码
     * @param
     * @return
     */
    @RequestMapping(value = "/loadUserMaxOrderNum",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String, Object> map=new HashMap<String, Object>();
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<SysUser> list = this.iSysUserService.list(queryWrapper);
        if(list.size()>0) {
            map.put("value", list.get(0).getOrdernum()+1);
        }else {
            map.put("value", 1);
        }
        return map;
    }


    /**
     * 根据用户信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public ResultObject updateUser (UserVO vo) {
        try {
            vo.setHiredate(LocalDateTime.now());
            this.iSysUserService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.UPDATE_ERROR;
        }
    }


    /**
     * 根据ID查询用户信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadUserById",method = RequestMethod.GET)
    public DataGridView loadUserById (UserVO vo) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null != vo.getId(),"id",vo.getId());
        SysUser one = this.iSysUserService.getOne(queryWrapper);
        return new DataGridView(one);
    }


    /**
     * 通过部门ID加载用户信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/loadUsersByDeptId",method = RequestMethod.GET)
    public DataGridView loadUsersByDeptId (UserVO vo) {
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(null != vo.getDeptid(),"deptid",vo.getDeptid());
        List<SysUser> list = this.iSysUserService.list(queryWrapper);
        return new DataGridView(list);
    }


    /**
     * 把用户名字改为拼音
     * @param username
     * @return
     */
    @RequestMapping(value = "/changeChineseToPinyin",method = RequestMethod.GET)
    public Map<String,Object> changeChineseToPinyin(String username){
        Map<String,Object> map=new HashMap<>();
        if(null!=username) {
            map.put("value", PinyinUtils.getPingYin(username));
        }else {
            map.put("value", "");
        }
        return map;
    }


    /**
     * 初始化角色表
     * @param vo
     * @return
     */
    @RequestMapping(value = "/initRoleByUserId",method = RequestMethod.GET)
    public DataGridView initRoleByUserId (RoleVO vo) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.ge(null != vo.getId(),"id",vo.getId());
        List<SysRole> list = this.sysRoleService.list(wrapper);
        return new DataGridView(list);
    }


    @RequestMapping(value = "/saveUserRole",method = RequestMethod.POST)
    public ResultObject saveUserRole (Integer uid,Integer [] ids) {
        return null;
    }
}
