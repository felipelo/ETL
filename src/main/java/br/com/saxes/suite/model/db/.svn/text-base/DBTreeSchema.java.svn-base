package br.com.saxes.suite.model.db;

import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import java.util.ArrayList;
import java.util.HashMap;

public class DBTreeSchema extends TreeSchema {

    private ConnectionWrapper conn;
    private WriteProperties writeProps;

    public DBTreeSchema() {
        super();
        writeProps = new WriteProperties();
    }

    public TableTreeNode getRootTable() {
        return (TableTreeNode) getRoot();
    }
    
    public TableTreeNode[] getUsedTables() {
        HashMap<String, TableTreeNode> usedTables = new HashMap<String, TableTreeNode>();
        
        TreeNode[] mappedColumns = getMappedTreeNodes();
        for( TreeNode mappedColumn : mappedColumns ) {
            //should be aways a TableTreeNode
            TableTreeNode tableTreeNode = (TableTreeNode) mappedColumn.getParentTreeNode();
            usedTables.put(tableTreeNode.getName(), tableTreeNode);
        }

        return usedTables.values().toArray( new TableTreeNode[usedTables.size()] );
    }

    @Override
    protected TreeNode getSpecMappedTreeNodes( TreeNode node ) {
        TreeNode _node = node;

        if( node instanceof FKTreeNode ) {
            FKTreeNode _fk = (FKTreeNode) node;
            _node = _fk.getForeignTable();
        }
        
        return _node;
    }

    /*
     * Getters & Setters methods
     */

    public ConnectionWrapper getConn() { return conn; }
    public void setConn(ConnectionWrapper conn) { this.conn = conn; }

    public WriteProperties getWriteProps() { return writeProps; }
    public void setWriteProps(WriteProperties writeProps) { this.writeProps = writeProps; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DBTreeSchema clone = new DBTreeSchema();

        clone( clone );

        return clone;
    }

    protected void clone( DBTreeSchema treeSchema ) throws CloneNotSupportedException {
        super.clone(treeSchema);
        treeSchema.setConn((ConnectionWrapper) getConn().clone());
        treeSchema.setWriteProps((WriteProperties) writeProps.clone());
    }

}
