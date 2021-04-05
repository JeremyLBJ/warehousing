package com.rick.sys.common;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 */
public class BuilderTreeNode {

    /**
     * 构建层级关系
     * @param treeNodes  数据
     * @param topPid     顶部ID
     * @return
     */
    public static List<TreeNode> builderTree (List<TreeNode> treeNodes , Integer topPid) {
        List<TreeNode> nodes = new ArrayList<>();
        // 找出pid为topPid的所有数据
        for (TreeNode t1: treeNodes) {
            if (topPid == t1.getPid()) {
                nodes.add(t1);
            }
            //找出pid 为t1 ID的所有数据
            for (TreeNode t2: treeNodes) {
                if (t1.getId() == t2.getPid()) {
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodes;
    }
}
