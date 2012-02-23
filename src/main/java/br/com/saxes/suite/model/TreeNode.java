package br.com.saxes.suite.model;

import br.com.saxes.suite.converter.ValueType;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This abstract class is the basic class for any value representation.
 *
 * @author Felipe
 */
public class TreeNode {

    /*An unic value for the whole project*/
    private String id;
    private String name;
    private String description;
    /*value type. Text, Date, Numeric, etc...*/
    private ValueType valueType;

    private ArrayList<TreeNode> childs;

    /*Its not save in the project file*/
    private transient TreeNode parentTreeNode;

    public TreeNode() {
        childs = new ArrayList<TreeNode>();
    }

    /**
     * Add a child in this TreeNode and sets the parent.
     * Any kind of check is made.
     *
     * @param child
     */
    public void addChild( TreeNode child ) {
        child.setParentTreeNode(this);

        childs.add(child);
    }

    @XmlTransient
    public TreeNode getParentTreeNode() { return parentTreeNode; }
    public void setParentTreeNode(TreeNode parentTreeNode) { this.parentTreeNode = parentTreeNode; }

    /*
     * Getters & Setters methods
     */
    @XmlID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ValueType getValueType() { return valueType; }
    public void setValueType(ValueType valueType) { this.valueType = valueType; }

    /**
     * This method should not use with the propose to add or remove elements from this list.
     * If you need to work with the mapping list, you should use the TreeNodeService class.
     * @see TreeNodeService
     * @return
     */
    public ArrayList<TreeNode> getChilds() { return childs; }
    public void setChilds(ArrayList<TreeNode> childs) { this.childs = childs; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected void clone( TreeNode valued ) throws CloneNotSupportedException {
        valued.setId(id);
        valued.setName(name);
        valued.setDescription(description);
        valued.setValueType(valueType);

        ArrayList<TreeNode> _childs = getChilds();
        for( TreeNode childClone : _childs ) {
            valued.addChild((TreeNode) childClone.clone());
        }

    }

}
