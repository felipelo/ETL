package br.com.saxes.suite.model.db;

import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlTransient;

public class TableTreeNode extends TreeNode {

    private ArrayList<FKTreeNode> foreignKeys;
    private ArrayList<UpdateCondition> conditions;
    
    private int writeProp;

    public TableTreeNode() {
        super();

        foreignKeys = new ArrayList<FKTreeNode>();
        conditions = new ArrayList<UpdateCondition>();
        writeProp = WriteProperties.INSERT;
    }

    public void addUpdateCondition( UpdateCondition updateCond ) {
        conditions.add( updateCond );
    }

    @Override
    public void addChild(TreeNode child) {
        super.addChild(child);
        if( child instanceof FKTreeNode ) {
            foreignKeys.add( (FKTreeNode) child );
        }
    }

    public TextTreeNode[] getMappedColumns() {
        ArrayList<TreeNode> _childs = getChilds();
        ArrayList<TextTreeNode> _mappedColumns = new ArrayList<TextTreeNode>( _childs.size() );
        for( TreeNode _node : _childs ) {
            if( _node instanceof TextTreeNode ) {
                TextTreeNode _textNode = (TextTreeNode) _node;
                if( _textNode.isMapped() )
                    _mappedColumns.add( _textNode );
            }
        }

        return _mappedColumns.toArray(new TextTreeNode[_mappedColumns.size()]);
    }

    @XmlTransient
    public ArrayList<FKTreeNode> getForeignKeys() { return new ArrayList<FKTreeNode>(foreignKeys); }
    public void setForeignKeys(ArrayList<FKTreeNode> foreignKeys) { this.foreignKeys = foreignKeys; }

    public ArrayList<UpdateCondition> getConditions() { return new ArrayList<UpdateCondition>(conditions); }
    public void setConditions(ArrayList<UpdateCondition> conditions) { this.conditions = conditions; }

    public int getWriteProp() { return writeProp; }
    public void setWriteProp(int writeProp) { this.writeProp = writeProp; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TableTreeNode clone = new TableTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( TableTreeNode table ) throws CloneNotSupportedException {
        super.clone( table );

        ArrayList<FKTreeNode> _fks = new ArrayList<FKTreeNode>( foreignKeys.size() );
        for( FKTreeNode _fk : foreignKeys ) {
            _fks.add( (FKTreeNode) _fk.clone());
        }
        table.setForeignKeys( _fks );

        ArrayList<UpdateCondition> _conditions = new ArrayList<UpdateCondition>( conditions.size() );
        for( UpdateCondition _where : conditions ) {
            _conditions.add( (UpdateCondition) _where.clone());
        }
        table.setConditions( _conditions );
    }

}