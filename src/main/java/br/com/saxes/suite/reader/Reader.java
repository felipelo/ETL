package br.com.saxes.suite.reader;

import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.converter.TreeSchemaPool;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.pool.ObjectPool;

public abstract class Reader implements Runnable {

    protected static final Logger log = Logger.getLogger( Reader.class.getName() );

    private static final int BUFFER_SIZE = 100;

    protected boolean finished;

    protected final List<TreeSchema> buffer;

    protected ObjectPool treeSchemaPool;
    protected SimpleDateFormat systemDateFormat;
    protected DecimalFormat systemNumFormat;

    private LinkedList<Exception> errors;

    public Reader( TreeSchema treeSchema ) throws ReaderInitException {
        log.info("Iniciando leitura...");

        if( treeSchema == null ) {
            throw new ReaderInitException(null, new NullPointerException("'treeSchema' can't be null."));
        }

        finished = false;
        
        buffer = Collections.synchronizedList( new ArrayList<TreeSchema>(BUFFER_SIZE) );
        treeSchemaPool = new TreeSchemaPool( treeSchema );

        log.fine("Padrão de data do systema: ".concat(System.getProperty("saxessuite.systemDatePattern")));
        log.fine("Padrão de número do systema: ".concat(System.getProperty("saxessuite.systemNumPattern")));

        systemDateFormat = new SimpleDateFormat( System.getProperty("saxessuite.systemDatePattern") );
        systemNumFormat = new DecimalFormat( System.getProperty("saxessuite.systemNumPattern") );

        errors = new LinkedList<Exception>();
    }

    public boolean hasFinished() {
		System.out.println(Thread.currentThread().getName() + " hasFinished()");
        return finished;
    }

    public TreeSchema next() throws IndexOutOfBoundsException {
        TreeSchema treeNode = null;
		
		while( buffer.isEmpty() ) {
			synchronized( buffer ) {
				
			}
		}

        if( !buffer.isEmpty() ) {
			System.out.println(Thread.currentThread().getName() + " Tamanho do buffer: ".concat(String.valueOf(buffer.size())));
            log.finest("Tamanho do buffer: ".concat(String.valueOf(buffer.size())));
            treeNode = buffer.remove(0);

            //used to notify a thread that one spot are available
            synchronized( buffer ) {
				System.out.println(Thread.currentThread().getName() + " notifing All()");
                buffer.notifyAll();
            }
        } else {
            /*
             * This exception should never be throws.
             * Before to call this method, check if the buffer is already empty
             * using 'hasMore()' method.
             */
            throw new IndexOutOfBoundsException();
        }

        return treeNode;
    }

    public boolean isFull() {
	System.out.println(Thread.currentThread().getName() + " isFull():RE " + buffer.size());
        return ((BUFFER_SIZE - buffer.size()) == 0);
    }

    public int bufferSize() {
	System.out.println(Thread.currentThread().getName() + " bufferSize:RE ".concat(String.valueOf(buffer.size())));
        return buffer.size();
    }

    public boolean hasMore() {
	System.out.println(Thread.currentThread().getName() + " hasMore():RE ");
        return !buffer.isEmpty();
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
