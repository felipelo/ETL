package br.com.saxes.suite.reader;

import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.converter.TreeSchemaPool;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import org.apache.commons.pool.ObjectPool;

public abstract class Reader implements Runnable {

    protected static final Logger log = Logger.getLogger( Reader.class.getName() );

    private static final int BUFFER_SIZE = 100;

    protected TreeSchema finished;

    protected final BlockingQueue<TreeSchema> buffer;

    protected ObjectPool treeSchemaPool;
    protected SimpleDateFormat systemDateFormat;
    protected DecimalFormat systemNumFormat;

    private LinkedList<Exception> errors;

    public Reader( TreeSchema treeSchema, TreeSchema finished ) throws ReaderInitException {
        log.info("Iniciando leitura...");

        if( treeSchema == null ) {
            throw new ReaderInitException(null, new NullPointerException("'treeSchema' can't be null."));
        }

        this.finished = finished;
        
        buffer = new ArrayBlockingQueue<TreeSchema>( BUFFER_SIZE );
        treeSchemaPool = new TreeSchemaPool( treeSchema );

        log.fine("Padrão de data do systema: ".concat(System.getProperty("saxessuite.systemDatePattern")));
        log.fine("Padrão de número do systema: ".concat(System.getProperty("saxessuite.systemNumPattern")));

        systemDateFormat = new SimpleDateFormat( System.getProperty("saxessuite.systemDatePattern") );
        systemNumFormat = new DecimalFormat( System.getProperty("saxessuite.systemNumPattern") );

        errors = new LinkedList<Exception>();
    }

    public TreeSchema next() throws InterruptedException {
		return buffer.take();
    }
	
	protected void setFinished() throws InterruptedException {
		buffer.put( finished );
	}

    public void returnTreeSchema( TreeSchema treeSchema ) {
        log.finest("Devolvendo objeto para a pilha");
        try {
            treeSchemaPool.returnObject(treeSchema);
        } catch (Exception ex) {
        }
    }

    protected void addError( Exception error ) { errors.addLast( error ); }

    public boolean hasError() { return !errors.isEmpty(); }
    
    public Exception[] getErrors() {
        Exception[] _errors = new Exception[errors.size()];
        return errors.toArray( _errors );
    }

}
