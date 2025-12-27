package cn.csw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lier
 * @since 2022/11/29 19:04
 */
public class MultiTreeNode {

    private String name;

    private MultiTreeNode parent;

    private List<MultiTreeNode> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultiTreeNode getParent() {
        return parent;
    }

    public void setParent(MultiTreeNode parent) {
        parent.getChildren().add(this);
        this.parent = parent;
    }

    public List<MultiTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MultiTreeNode> children) {
        this.children = children;
    }


    public MultiTreeNode(String name) {
        this.name = name;
    }

    public MultiTreeNode() {
    }
}
