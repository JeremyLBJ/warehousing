package com.rick;

import com.rick.sys.entity.SysUser;
import com.rick.sys.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WarehousingApplicationTests {

    @Resource
    private SysUserMapper sysUserMapper;

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

}
