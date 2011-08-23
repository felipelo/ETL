package br.com.saxes.suite.converter;

import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Vector;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;

/**
 *
 * @author felipe
 */
public class TreeSchemaPool implements ObjectPool {

    private final TreeSchema model;
    private Vector<TreeSchema> pool;

    public TreeSchemaPool( TreeSchema model ) {
        this.model = model;
        pool = new Vector<TreeSchema>();
    }

    @Override
    public synchronized Object borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
        TreeSchema _toBorrow = null;
        if( pool.isEmpty() ) {
            _toBorrow = (TreeSchema) model.clone();
        } else {
            _toBorrow = pool.remove( 0 );
        }
//        System.out.println(Thread.currentThread().getName() + " : borrowing..." + pool.size() + " : " + _toBorrow);
        return _toBorrow;
    }

    @Override
    public synchronized void returnObject(Object obj) throws Exception {
        TreeSchema _toReturn = (TreeSchema) obj;
        cleanTreeNode( _toReturn.getRoot() );
        pool.addElement( _toReturn );
//        System.out.println(Thread.currentThread().getName() + " : returning..." + pool.size() + " : " + _toReturn);
    }

    private void cleanTreeNode( TreeNode node ) {
        if( node instanceof TextTreeNode )
            ((TextTreeNode)node).setValue(null);
        ArrayList<TreeNode> _childs = node.getChilds();
        for( TreeNode _node : _childs ) {
            cleanTreeNode( _node );
        }
    }

    @Override
    public void invalidateObject(Object obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumIdle() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getNumActive() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() throws Exception, UnsupportedOperationException {
        pool.clear();
    }

    @Override
    public void close() throws Exception {
        pool.clear();
    }

    @Override
    public void setFactory(PoolableObjectFactory factory) throws IllegalStateException, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
