package com.rick.sys.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rick.sys.VO.NoticeVO;
import com.rick.sys.common.DataGridView;
import com.rick.sys.common.ResultObject;
import com.rick.sys.entity.SysNotice;
import com.rick.sys.entity.SysUser;
import com.rick.sys.service.ISysNoticeService;
import com.rick.sys.untils.WebUntils;
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
 * 系统公告前端控制器
 */
@RequestMapping("/notice")
@Controller
public class NoticeController {

    @Resource
    private ISysNoticeService sysNoticeService ;


    /**
     * 根据多个组合条件查询
     * @param vo  接收前端数据
     * @return  返回对应数据
     */
    @RequestMapping(value = "/loadAllNotice",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView queryAllNotice (NoticeVO vo) {
        QueryWrapper<SysNotice> wrapper = new QueryWrapper<>();
        IPage<SysNotice> page = new Page<>(vo.getPage(),vo.getLimit());
        wrapper.like(StringUtils.isNotBlank(vo.getTitle()),"title",vo.getTitle());
        wrapper.like(StringUtils.isNotBlank(vo.getOpername()),"opername",vo.getOpername());
        wrapper.ge(vo.getStartTime()!=null,"createtime",vo.getStartTime());
        wrapper.le(vo.getEndTime()!=null,"createtime",vo.getEndTime());
        wrapper.orderByDesc("createtime");
        this.sysNoticeService.page(page,wrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }



    @RequestMapping(value = "/queryNoticeById",method = RequestMethod.GET)
    @ResponseBody
    public DataGridView queryNoticeById (NoticeVO vo) {
        SysNotice byId = this.sysNoticeService.getById(vo.getId());
        return new DataGridView(byId);
    }


    /**
     * 根据ID移除数据
     * @param vo  接收前端数据对象
     * @return  返回对应状态码
     */
    @RequestMapping(value = "/removeById",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject removeById (NoticeVO vo) {
        try {
            this.sysNoticeService.removeById(vo.getId());
            return ResultObject.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    /**
     * 批量移除
     * @param vo 接受前端传入数据
     * @return   返回对应状态码
     */
    @RequestMapping(value = "/batchRemoveByIds",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject batchRemoveByIds (NoticeVO vo) {
        try {
            List<Integer> idList = new ArrayList<>();
            for (Integer id: vo.getIds()) {
                idList.add(id);
            }
            this.sysNoticeService.removeByIds(idList);
            return ResultObject.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }


    /**
     * 保存系统公告
     * @param vo
     * @return
     */
    @RequestMapping(value = "/saveNotice",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject saveNotice (NoticeVO vo) {
        try {
            vo.setCreatetime(LocalDateTime.now());
            SysUser u = (SysUser) WebUntils.getSession().getAttribute("user");
            vo.setOpername(u.getName());
            this.sysNoticeService.save(vo);
            return ResultObject.SAVE_SUCCESS;
        } catch(Exception e){
            e.printStackTrace();
            return ResultObject.SAVE_ERROR;
        }
    }


    /**
     * 修改公告
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateNotice",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateNotice (NoticeVO vo) {
        try {
            vo.setCreatetime(LocalDateTime.now());
            this.sysNoticeService.updateById(vo);
            return ResultObject.UPDATE_SUCCESS;
        }catch (Exception e) {
            e.printStackTrace();
            return ResultObject.DELETE_ERROR;
        }
    }
}
