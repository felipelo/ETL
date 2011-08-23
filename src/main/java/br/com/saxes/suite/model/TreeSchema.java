package br.com.saxes.suite.model;

import java.util.ArrayList;

public abstract class TreeSchema {

    private String name;
    private TreeNode root;

    public TreeNode getTreeNodeByID( String id ) {
        return getTreeNodeByID( id, root );
    }

    private TreeNode getTreeNodeByID( String id, TreeNode root ) {
        if( root.getId().equals(id) ) {
            return root;
        } else {
            for( TreeNode _node : root.getChilds() )
                return getTreeNodeByID( id, _node );
        }

        return null;
    }

    public TextTreeNode[] getMappedTreeNodes() {
        ArrayList<TextTreeNode> mappedNodes = new ArrayList<TextTreeNode>();

        ArrayList<TreeNode> childs = root.getChilds();
        getMappedTreeNodes(childs, mappedNodes);

        TextTreeNode[] mappeds = new TextTreeNode[mappedNodes.size()];
        return mappedNodes.toArray(mappeds);
    }

    private synchronized void getMappedTreeNodes( ArrayList<TreeNode> childs, ArrayList<TextTreeNode> mappedNodes ) {
        if( childs.isEmpty() ) {
            return;
        }
        
        for( int x = 0; x < childs.size(); x++ ) {
            TreeNode _node = childs.get( x );
            if( _node instanceof TextTreeNode ) {
                TextTreeNode _child = (TextTreeNode) _node;
                if( _child.isMapped() ) {
                    mappedNodes.add(_child);
                }
                getMappedTreeNodes(_child.getChilds(), mappedNodes);
            } else {
                TreeNode _spec = getSpecMappedTreeNodes( _node );
                getMappedTreeNodes( _spec.getChilds(), mappedNodes );
            }

        }
    }

    protected abstract TreeNode getSpecMappedTreeNodes( TreeNode node );

    /*
     * Getters & Setters methods
     */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TreeNode getRoot() { return root; }
    public void setRoot(TreeNode root) { this.root = root; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    protected void clone( TreeSchema treeSchema ) throws CloneNotSupportedException {
        treeSchema.setName( name );
        treeSchema.setRoot( (TreeNode) root.clone());
    }
}
