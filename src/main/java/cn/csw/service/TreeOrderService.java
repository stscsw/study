package cn.csw.service;

import cn.csw.model.TreeNode;

import java.util.LinkedList;
import java.util.Objects;


/**
 * @author Lier
 * @since 2022/11/29 15:42
 */
public class TreeOrderService {
    public static void main(String[] args) {
        System.out.println("前序遍历");
        TreeNode[] treeNodes = InitTree.initTree();
        preOrder(treeNodes[0]);
        System.out.println();
        System.out.println("中序遍历");
        inOrder(treeNodes[0]);
        System.out.println();
        System.out.println("后序遍历");
        postOrder(treeNodes[0]);
        System.out.println();
        System.out.println("层序遍历");
        floorOrder(treeNodes[0]);
    }

    /**
     * 前序遍历  根节点-》左节点-》右节点
     * 二叉树的前序遍历算法
     * 1.判断节点为空，返回空；
     * 2.打印节点数据；
     * 3.判断是否存在左子树，如果存在，递归，回到步骤1；
     * 4.判断是否存在右子树，如果存在，递归，回到步骤1；
     */
    public static void preOrder(TreeNode treeNode) {
        if (null == treeNode) {
            return;
        }
        System.out.print(treeNode.getValue());
        TreeNode leftNode = treeNode.getLeft();
        preOrder(leftNode);
        TreeNode rightNode = treeNode.getRight();
        preOrder(rightNode);
    }


    /**
     * 中序遍历  左节点-》根节点-》右节点
     * 二叉树的中序遍历算法
     * 1.判断节点为空，返回空；
     * 2.判断是否存在左子树，如果存在，递归，回到步骤1；
     * 3.打印节点数据；
     * 4.判断是否存在右子树，如果存在，递归，回到步骤1；
     */
    public static void inOrder(TreeNode treeNode) {
        if (null == treeNode) {
            return;
        }
        TreeNode leftNode = treeNode.getLeft();
        inOrder(leftNode);
        System.out.print(treeNode.getValue());
        TreeNode rightNode = treeNode.getRight();
        inOrder(rightNode);
    }


    /**
     * 二叉树的后续遍历算法
     * 1.判断节点为空，返回空；
     * 2.判断是否存在左子树，如果存在，递归，回到步骤1；
     * 3.判断是否存在右子树，如果存在，递归，回到步骤1；
     * 4.打印节点数据；
     */
    public static void postOrder(TreeNode treeNode) {
        if (null == treeNode) {
            return;
        }
        TreeNode leftNode = treeNode.getLeft();
        postOrder(leftNode);
        TreeNode rightNode = treeNode.getRight();
        postOrder(rightNode);
        System.out.print(treeNode.getValue());
    }


    public static void floorOrder(TreeNode treeNode){
        if (null == treeNode) {
            return;
        }
        LinkedList<TreeNode> list = new LinkedList<>();
        list.add(treeNode);
        while (!list.isEmpty()){
            TreeNode poll = list.poll();
            System.out.print(poll.getValue());
            TreeNode left = poll.getLeft();
            if (Objects.nonNull(left)){
                list.add(left);
            }
            TreeNode right = poll.getRight();
            if(Objects.nonNull(right)){
                list.add(right);
            }
        }
    }

}
