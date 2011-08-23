package br.com.saxes.suite.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(value=XmlAccessType.FIELD)
public class Mapping {

    private String description;
    @XmlIDREF private TextTreeNode sourceTreeNode;
    @XmlIDREF private TextTreeNode targetTreeNode;

    public boolean containTreeNode( TextTreeNode treeNode ) {
        if( (treeNode == sourceTreeNode) || (treeNode == targetTreeNode) )
            return true;
        else 
            return false;
    }

    /*
     * Getters & Setters methods
     */

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TextTreeNode getSourceTreeNode() { return sourceTreeNode; }
    public void setSourceTreeNode(TextTreeNode sourceTreeNode) { this.sourceTreeNode = sourceTreeNode; }

    public TextTreeNode getTargetTreeNode() { return targetTreeNode; }
    public void setTargetTreeNode(TextTreeNode targetTreeNode) { this.targetTreeNode = targetTreeNode; }
}
