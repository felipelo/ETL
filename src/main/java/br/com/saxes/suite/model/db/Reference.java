package br.com.saxes.suite.model.db;

import br.com.saxes.suite.model.TextTreeNode;

/**
 *
 * @author Felipe
 */
public class Reference {

    private TextTreeNode localColumn;
    private TextTreeNode foreignColumn;
    
    public Reference() {}
    
    public Reference( TextTreeNode localColumn, TextTreeNode foreignColumn ) {
        this.localColumn = localColumn;
        this.foreignColumn = foreignColumn;
    }

    public TextTreeNode getForeignColumn() { return foreignColumn; }
    public void setForeignColumn(TextTreeNode foreignColumn) { this.foreignColumn = foreignColumn; }

    public TextTreeNode getLocalColumn() { return localColumn; }
    public void setLocalColumn(TextTreeNode localColumn) { this.localColumn = localColumn; }
    
}
