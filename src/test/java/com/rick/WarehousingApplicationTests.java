package com.rick;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysUser;
import com.rick.sys.mapper.SysUserMapper;
import com.rick.sys.service.ISysPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WarehousingApplicationTests {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ISysPermissionService sysPermissionService;

    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("address","武汉");
        List<SysUser> sysUsers = this.sysUserMapper.selectByMap(map);
        print(sysUsers);

    }

    public void print(List<SysUser> sysUsers){
        for (SysUser user: sysUsers
             ) {
            System.out.println(user);
        }
    }

    @Test
    void test(){
        System.out.println("success");
    }


    @Test
    void buildTreeTest () {
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<SysPermission>();
        wrapper.eq("type","menu");
        wrapper.eq("available",1);
        List<SysPermission> sysPermissions = this.sysPermissionService.list(wrapper);
        // 循环添加到集合中 此时还没有层级关系


        //创建一个类 在类中构造层级关系
    }

}
