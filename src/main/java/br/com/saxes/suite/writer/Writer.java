package br.com.saxes.suite.writer;

import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.converter.TreeSchemaPool;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import org.apache.commons.pool.ObjectPool;

public abstract class Writer implements Runnable {

    protected static final Logger log = Logger.getLogger( Writer.class.getName() );

    protected final BlockingQueue<TreeSchema> buffer;

    protected ObjectPool treeSchemaPool;

    protected SimpleDateFormat systemDateFormat;
    protected DecimalFormat systemNumFormat;

    protected TreeSchema finished;

    public Writer( TreeSchema treeSchema, TreeSchema finished ) {
        if( treeSchema == null ) {
            throw new WriterInitException(null, new NullPointerException("'treeSchema' can't be null."));
        }

        buffer = new ArrayBlockingQueue<TreeSchema>( 100 );
        treeSchemaPool = new TreeSchemaPool( treeSchema );
		this.finished = finished;

        systemDateFormat = new SimpleDateFormat( System.getProperty("saxessuite.systemDatePattern") );
        systemNumFormat = new DecimalFormat( System.getProperty("saxessuite.systemNumPattern") );
    }

    public void add( TreeSchema treeSchema ) throws InterruptedException {
        buffer.put(treeSchema);
    }

    public void setFinished() throws InterruptedException {
		buffer.put( finished );
    }

    public TreeSchema borrowTreeSchema() throws Exception {
        return (TreeSchema) treeSchemaPool.borrowObject();
    }

}
