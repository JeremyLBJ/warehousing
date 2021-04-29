package com.rick;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rick.sys.common.BuilderTreeNode;
import com.rick.sys.common.Constant;
import com.rick.sys.common.TreeNode;
import com.rick.sys.entity.SysPermission;
import com.rick.sys.entity.SysUser;
import com.rick.sys.mapper.SysRolePermissionMapper;
import com.rick.sys.mapper.SysUserMapper;
import com.rick.sys.service.ISysPermissionService;
import com.rick.sys.service.ISysRolePermissionService;
import com.rick.sys.service.ISysRoleUserService;
import com.rick.sys.untils.WebUntils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;
import java.security.Permission;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
class WarehousingApplicationTests {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ISysRolePermissionService iSysRolePermissionService;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Resource
    private ISysPermissionService sysPermissionService;


    @Resource
    private ISysRoleUserService roleUserService;

    @Resource
    private ISysRolePermissionService permissionService;


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
    void rolePermissionTest () {
//        List<Integer> integers = this.sysRolePermissionMapper.queryPidByRid(5);
//        for (Integer in: integers
//             ) {
//            System.out.println(in);
//        }
    }
    // 需要传一个vo
    @Test
    void buildTreeTest () {
//        QueryWrapper<SysPermission> wrapper = new QueryWrapper<SysPermission>();
//        wrapper.eq("type",Constant.MENU);
//        wrapper.eq("available",Constant.AVAILABLE);
//        // 条件拼接  需要判断用户是否为最高管理员
//        SysUser user = (SysUser) WebUntils.getSession().getAttribute("user");
//        List<SysPermission> sysPermissions = null;
//        if (Constant.SUPER_USER.equals(user.getType())) {
//            //超级管理员  拥有所有权限
//            sysPermissions = this.sysPermissionService.list(wrapper);
//        } else {
//            // 普通管理员  通过管理员ID+权限ID+角色ID查询出来
//            sysPermissions = this.sysPermissionService.list(wrapper);
//        }
//        // 循环添加到集合中 此时还没有层级关系
//        List<TreeNode> sysPermissionList = new ArrayList<>();
//        for (SysPermission p: sysPermissions
//             ) {
//            Integer id = p.getId();
//            Integer pid = p.getPid();
//            String title = p.getTitle();
//            String icon = p.getIcon();
//            String href = p.getHref();
//            Boolean spread = p.getOpen().equals(Constant.IS_OPEN) ?true:false;
//            sysPermissionList.add(new TreeNode( id,  pid,  title,  icon,  href,  spread));
//        }
//        //创建一个类 在类中构造层级关系
//        List<TreeNode> treeNode = BuilderTreeNode.builderTree(sysPermissionList,1);
//        System.out.println(treeNode);
    }

    @Test
    void locaTimeTest () {
        String format = "YYYY-MM-dd hh:mm:ss";
        // DateTimeFormatter.ofPattern方法根据指定的格式输出时间
        String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        System.out.println(LocalDateTime.now());
        System.out.println(new Date());
        System.out.println(formatDateTime);
    }


    @Test
    void permissionTest () {
        QueryWrapper<SysPermission> qw = new QueryWrapper<>();
        qw.eq("type", Constant.PERMISSION);
        qw.eq("available",Constant.AVAILABLE);
        List<SysPermission> list = null;
        if (Constant.SUPER_USER.equals(1)) {
            // 超级管理员
            list = this.sysPermissionService.list(qw);
        }else {
            // 普通管理者
            //根据用户ID查询对应的角色ID
            List<Integer> integers = this.roleUserService.queryRidByUid(6);
            if (integers.size() > 0){
                //根据角色ID取到权限和菜单ID
                Set<Integer> pids=new HashSet<>();
                for (Integer rid : integers) {
                    //根据角色ID查询对应角色所拥有的的菜单和权限
                    List<Integer> permissionIds = permissionService.queryRolePermissionIdsByRid(rid);
                    pids.addAll(permissionIds);
                }
                //根据角色ID查询权限
                if(pids.size()>0) {
                    qw.in("id", pids);
                    list = sysPermissionService.list(qw);
                }else {
                    list =new ArrayList<>();
                }
            }
        }
        List<String> percode = new ArrayList<>();
        for (SysPermission sysPermission: list) {
            percode.add(sysPermission.getPercode());
        }
        System.out.println(Arrays.asList(percode));
    }


    @Test
    void round() {
        Set set = new HashSet();
        Set rounds = rounds(set);
        System.out.println(rounds);
    }


    public Set rounds (Set set) {
        if (set.size() == 4)
            return set;
        Random random = new Random();
        int i = random.nextInt(10);
        set.add(i);
        System.out.println(i);
        return rounds(set);
    }

}
