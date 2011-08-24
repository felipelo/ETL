package br.com.saxes.suite.model.db;

import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author Felipe
 */
public class FKTreeNode extends TreeNode {

    private TableTreeNode foreignTable;

    private ArrayList<Reference> references;
    private TextTreeNode localColumn;
    private TextTreeNode foreignColumn;

    private JoinStrategy joinStrategy;
    
    public FKTreeNode(){
        references = new ArrayList<Reference>();
    }
    
    public void addReference( Reference reference ) {
        references.add( reference );
    }

    @XmlIDREF
    public TextTreeNode getForeignColumn() { return foreignColumn; }
    public void setForeignColumn(TextTreeNode foreignColumn) { this.foreignColumn = foreignColumn; }

    public TableTreeNode getForeignTable() { return foreignTable; }
    public void setForeignTable(TableTreeNode foreignTable) {
        this.foreignTable = foreignTable;
        addChild( foreignTable );
    }

    @XmlIDREF
    public TextTreeNode getLocalColumn() { return localColumn; }
    public void setLocalColumn(TextTreeNode localColumn) { this.localColumn = localColumn; }

    public JoinStrategy getJoinStrategy() { return joinStrategy; }
    public void setJoinStrategy(JoinStrategy joinStrategy) { this.joinStrategy = joinStrategy; }

    public ArrayList<Reference> getReferences() { return references; }
    public void setReferences(ArrayList<Reference> references) { this.references = references; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FKTreeNode clone = new FKTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( FKTreeNode fk ) throws CloneNotSupportedException {
        super.clone(fk);
        fk.setForeignColumn((TextTreeNode) foreignColumn.clone());
        fk.setForeignTable((TableTreeNode) foreignTable.clone());
        fk.setLocalColumn((TextTreeNode) localColumn.clone());
        fk.setJoinStrategy(joinStrategy);
    }

}
