package cn.csw.model;

/**
 * @author Lier
 * @since 2022/11/29 15:38
 */

/**
 * 树结构，包括节点值，左子树节点指针，右子树节点指针
 */
public class TreeNode {

    private String value;
    private TreeNode left;
    private TreeNode right;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

}