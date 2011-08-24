package br.com.saxes.suite.writer;

import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.converter.TreeSchemaPool;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.pool.ObjectPool;

public abstract class Writer implements Runnable {

    protected static final Logger log = Logger.getLogger( Writer.class.getName() );

    protected final List<TreeSchema> buffer;

    protected ObjectPool treeSchemaPool;

    protected SimpleDateFormat systemDateFormat;
    protected DecimalFormat systemNumFormat;

    protected boolean finished;

    public Writer( TreeSchema treeSchema ) {
        if( treeSchema == null ) {
            throw new WriterInitException(null, new NullPointerException("'treeSchema' can't be null."));
        }

        buffer = Collections.synchronizedList( new ArrayList<TreeSchema>() );
        treeSchemaPool = new TreeSchemaPool( treeSchema );

        systemDateFormat = new SimpleDateFormat( System.getProperty("saxessuite.systemDatePattern") );
        systemNumFormat = new DecimalFormat( System.getProperty("saxessuite.systemNumPattern") );
    }

    public void add( TreeSchema treeSchema ) {
        buffer.add(treeSchema);
        
        synchronized (buffer) {
            buffer.notifyAll();
        }
    }

    public int bufferSize() {
        return buffer.size();
    }

    public void setFinished( boolean finished ) {
        this.finished = finished;
        
        synchronized (this) {
            notifyAll();
        }
    }

    public TreeSchema borrowTreeSchema() throws Exception {
        return (TreeSchema) treeSchemaPool.borrowObject();
    }

}
