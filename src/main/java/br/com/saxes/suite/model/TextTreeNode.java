package br.com.saxes.suite.model;

import br.com.saxes.suite.converter.ValueType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * This class represent any text value. It is a basic implementation of class TreeNode.
 *
 * @author Felipe
 */
public class TextTreeNode extends TreeNode {

    /*Its not save in the project file*/
    protected transient String value;
    /*Its not save in the project file*/
    private transient boolean mapped;

    public TextTreeNode() {
        super();
        setValueType(ValueType.TEXT);
        mapped = false;
    }

    /**
     * Returns the value as it is. No modifications!
     *
     * @return value
     */
    public String getValue() { return value; }

    /**
     * Sets the value as it is. No modifications!
     *
     * @param value
     */
    public void setValue( String value ) { this.value = value; }

    @XmlTransient
    public boolean isMapped() { return mapped; }
    public void setMapped(boolean mapped) { this.mapped = mapped; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TextTreeNode clone = new TextTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( TextTreeNode node ) throws CloneNotSupportedException {
        super.clone( node );
        node.setMapped(mapped);
    }

}
