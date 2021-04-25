package com.rick.sys.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rick.sys.entity.SysPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TreeNode {

    private Integer id;
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<TreeNode> children=new ArrayList<>();
    private String checkArr = "0";
    /**
     *首页左边导航树的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }


    /**
     *首页左边部门树的构造器
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
    }

    /**
     *首页左边部门树的构造器
     */
    public TreeNode(Integer id, Integer pid, String title,String checkArr) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.checkArr = checkArr;
    }

}
