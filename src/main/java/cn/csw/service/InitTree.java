package cn.csw.service;

/**
 * @author Lier
 * @since 2022/11/29 15:41
 */

import cn.csw.model.MultiTreeNode;
import cn.csw.model.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 初始化树的节点信息
 */
public class InitTree {


    public static MultiTreeNode initMultiTreeNode() {

        MultiTreeNode a = new MultiTreeNode("A");
        MultiTreeNode b = new MultiTreeNode("B");
        MultiTreeNode c = new MultiTreeNode("C");
        MultiTreeNode d = new MultiTreeNode("D");
        MultiTreeNode e = new MultiTreeNode("E");
        MultiTreeNode f = new MultiTreeNode("F");
        MultiTreeNode g = new MultiTreeNode("G");
        MultiTreeNode h = new MultiTreeNode("H");
        MultiTreeNode i = new MultiTreeNode("I");
        MultiTreeNode j = new MultiTreeNode("J");

        h.setParent(e);
        i.setParent(e);
        j.setParent(e);
        e.setParent(b);
        f.setParent(b);
        b.setParent(a);
        c.setParent(a);
        g.setParent(d);
        d.setParent(a);

        return a;
    }

    public static TreeNode[] initTree() {
        TreeNode[] treeNode = new TreeNode[11];
        createInstance(treeNode);
        initNodeValue(treeNode);
        buildTree(treeNode);
        return treeNode;
    }

    // 创建节点对象
    private static void createInstance(TreeNode[] treeNode) {
        if (null != treeNode) {
            for (int i = 0; i < treeNode.length; i++) {
                treeNode[i] = new TreeNode();
            }
        }
    }

    // 初始化节点值
    private static void initNodeValue(TreeNode[] treeNode) {
        // 设置节点0-10的值
        treeNode[0].setValue("A");
        treeNode[1].setValue("B");
        treeNode[2].setValue("C");
        treeNode[3].setValue("D");
        treeNode[4].setValue("E");
        treeNode[5].setValue("F");
        treeNode[6].setValue("G");
        treeNode[7].setValue("H");
        treeNode[8].setValue("I");
        treeNode[9].setValue("J");
        treeNode[10].setValue("K");
    }

    // 构建树
    private static void buildTree(TreeNode[] treeNode) {
        // 初始化节点0的左子树和右子树
        treeNode[0].setLeft(treeNode[1]);
        treeNode[0].setRight(treeNode[2]);
        // 初始化节点1的左子树和右子树
        treeNode[1].setLeft(treeNode[3]);
        treeNode[1].setRight(treeNode[4]);
        // 初始化节点2的左子树和右子树
        treeNode[2].setLeft(treeNode[5]);
        treeNode[2].setRight(treeNode[6]);
        // 初始化节点3的左子树和右子树
        treeNode[3].setLeft(treeNode[7]);
        // 初始化节点4的左子树和右子树
        // 初始化节点5的左子树和右子树
        treeNode[5].setLeft(treeNode[8]);
        // 初始化节点6的左子树和右子树
        treeNode[6].setRight(treeNode[9]);
        // 初始化节点7的左子树和右子树
        treeNode[7].setRight(treeNode[10]);
        // 初始化节点8的左子树和右子树
        // 初始化节点9的左子树和右子树
        // 初始化节点10的左子树和右子树
    }


    public static void main(String[] args) {
        MultiTreeNode multiTreeNode = initMultiTreeNode();
        List<MultiTreeNode> list = new ArrayList<>();
        depthFirst(multiTreeNode, list);
        System.out.println();
        Collections.reverse(list);
        list.stream().map(MultiTreeNode::getName).forEach(System.out::print);

    }

    public static void depthFirst(MultiTreeNode root, List<MultiTreeNode> finalOrderedList) {
        Deque<MultiTreeNode> nodeDeque = new LinkedList<>();
        MultiTreeNode node = root;
        nodeDeque.push(node);
        while (!nodeDeque.isEmpty()) {
            node = nodeDeque.pop();
            finalOrderedList.add(node);
            System.out.print(node.getName());
            for (MultiTreeNode treeNode : node.getChildren()) {
                nodeDeque.push(treeNode);
            }
        }
    }


}