package br.com.saxes.suite.model.txt;

import br.com.saxes.suite.model.TextTreeNode;

/**
 *
 * @author felipe
 */
public class FixedTextTreeNode extends TextTreeNode {

    private int lenght;

    public FixedTextTreeNode() {
        super();
        lenght = 0;
    }

    public int getLenght() { return lenght; }
    public void setLenght(int lenght) { this.lenght = lenght; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FixedTextTreeNode clone = new FixedTextTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( FixedTextTreeNode node ) throws CloneNotSupportedException {
        super.clone( node );
    }

}
