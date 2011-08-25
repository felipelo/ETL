package br.com.saxes.suite.reader.txt;

import br.com.personal.flatfileparser.FixedWidthFFParser;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.txt.FixedTXTTreeSchema;
import br.com.saxes.suite.model.txt.FixedTextTreeNode;
import br.com.saxes.suite.reader.Reader;
import br.com.saxes.suite.reader.ReaderInitException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author felipe
 */
public class FixedTXTReader extends Reader {

    private FixedWidthFFParser parser;

    public FixedTXTReader(FixedTXTTreeSchema txtTreeSchema) throws ReaderInitException {
        super(txtTreeSchema);

        File _sourceFile = new File(txtTreeSchema.getFileRef().getFilePath());
        ArrayList<TreeNode> _nodes = txtTreeSchema.getRoot().getChilds();
        int _widthsColumns[] = new int[_nodes.size()];
        for (int _x = 0; _x < _nodes.size(); _x++) {
            if( _nodes.get(_x) instanceof FixedTextTreeNode ) {
                FixedTextTreeNode _fixedColumn = (FixedTextTreeNode) _nodes.get( _x );
                _widthsColumns[_x] = _fixedColumn.getLenght();
            }
        }

        try {
            parser = new FixedWidthFFParser(_sourceFile,
                    _widthsColumns,
                    txtTreeSchema.getHeaderLines(),
                    txtTreeSchema.getNewLine().length());
        } catch (FileNotFoundException ex) {
            throw new ReaderInitException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new ReaderInitException(ex.getMessage(), ex);
        }
    }

    @Override
    public void run() {
         try {
            while (parser.next()) {
                //if the buffer list is full, wait until it has at least one free spot.
                synchronized (buffer) {
                    while (isFull()) {
                        System.out.println(Thread.currentThread().getName() + "Buffer.isFull.... waiting " + buffer.size());
                        buffer.wait();
						System.out.println(Thread.currentThread().getName() + " Waking up");
                    }
                }

                FixedTXTTreeSchema _line = (FixedTXTTreeSchema) treeSchemaPool.borrowObject();

                ArrayList<TreeNode> _nodes = _line.getRoot().getChilds();
                for( int _x = 0; _x < _nodes.size(); _x++ ) {
                    TreeNode _n = _nodes.get( _x );
                    if( _n instanceof TextTreeNode ) {
                        TextTreeNode _mapped = (TextTreeNode) _n;
                        if( _mapped.isMapped() ) {
                            String _value = null;
                            _value = parser.getString( _x+1 );

                            _mapped.setValue( _value.trim() );

                            log.finest("Lendo dado: ".concat(_mapped.getName()).concat(": ").concat(_value));
                        }
                    }
                }

                buffer.add( _line );
            }
			while( !buffer.isEmpty() ) {
				synchronized( buffer ) {
					buffer.wait();
				}
			}
        } catch (Exception ex) {
            Logger.getLogger(FixedTXTReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                parser.close();
            } catch (Exception ex) {}
			
			finished = true;
        }
    }
}
