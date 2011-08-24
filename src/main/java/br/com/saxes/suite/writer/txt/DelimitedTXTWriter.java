package br.com.saxes.suite.writer.txt;

import br.com.saxes.suite.model.DateTreeNode;
import br.com.saxes.suite.model.NumericTreeNode;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.model.txt.DelimitedTXTTreeSchema;
import br.com.saxes.suite.model.txt.FileRef;
import br.com.saxes.suite.writer.Writer;
import br.com.saxes.suite.writer.WriterInitException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DelimitedTXTWriter extends Writer {
    
    private DelimitedTXTTreeSchema txtTreeSchema;

    private BufferedWriter fileDest;

    public DelimitedTXTWriter(DelimitedTXTTreeSchema txtTreeSchema) throws WriterInitException {
        super(txtTreeSchema);

        this.txtTreeSchema = txtTreeSchema;

        FileRef _fileRef = txtTreeSchema.getFileRef();

        try {
            FileWriter _fileOutput = new FileWriter(_fileRef.getFilePath(), true);
            fileDest = new BufferedWriter(_fileOutput);
        } catch( IOException ex ) {
            throw new WriterInitException("File not found", ex);
        }
    }

    @Override
    public void run() {
        try {
            while( !finished ) {
                synchronized( buffer ) {
                    while( buffer.isEmpty() && !finished ) {
                        buffer.wait();
                    }
                }

                TreeSchema _treeSchema = buffer.remove(0);

                TextTreeNode[] _mappedNodes = _treeSchema.getMappedTreeNodes();

                int _size = _mappedNodes.length-1;
                int _x = 0;
                for( _x = 0; _x < _size; _x++ ) {
                    write( _mappedNodes[_x] );
                    fileDest.write( txtTreeSchema.getColumnDelimiter() );
                }
                write( _mappedNodes[_x] );
                fileDest.write( txtTreeSchema.getLineDelimiter() );

                treeSchemaPool.returnObject( _treeSchema );
            }
        } catch( IOException ex ) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            //should not throw any exception
            ex.printStackTrace();
        } catch (Exception ex) {
            //should not throw any exception
            ex.printStackTrace();
        } finally {
            try {
                fileDest.close();
            } catch (IOException ex) {}
        }
    }

    private void write( TextTreeNode node ) throws IOException, ParseException {
        String _fieldDelimiter = "";
        String _fieldValue = null;
        
        switch( node.getValueType() ) {
            case TEXT:
                _fieldDelimiter = txtTreeSchema.getFieldQualifier();
                _fieldValue = node.getValue();
                break;
            case DATE:
                DateTreeNode _dateField = (DateTreeNode) node;
                _fieldValue = _dateField.getValue();
                SimpleDateFormat _dateFormat = new SimpleDateFormat( _dateField.getFieldDateFormat() );

                Date _date = systemDateFormat.parse( _fieldValue );
                _fieldValue = _dateFormat.format( _date );
                break;
            case NUMERIC:
                NumericTreeNode _numField = (NumericTreeNode) node;
                _fieldValue = _numField.getValue();
                DecimalFormat _numFormat = new DecimalFormat( _numField.getFieldNumFormat() );

                Double _numeric = systemNumFormat.parse( _fieldValue ).doubleValue();
                _fieldValue = _numFormat.format( _numeric );
        }
        
        fileDest.write( _fieldDelimiter );
        fileDest.write( _fieldValue );
        fileDest.write( _fieldDelimiter );
    }

}
