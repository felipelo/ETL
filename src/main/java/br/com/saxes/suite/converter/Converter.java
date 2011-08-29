package br.com.saxes.suite.converter;

import br.com.saxes.suite.model.Project;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.model.db.DBTreeSchema;
import br.com.saxes.suite.model.txt.DelimitedTXTTreeSchema;
import br.com.saxes.suite.model.txt.FixedTXTTreeSchema;
import br.com.saxes.suite.reader.Reader;
import br.com.saxes.suite.reader.db.DBReader;
import br.com.saxes.suite.reader.txt.FixedTXTReader;
import br.com.saxes.suite.writer.Writer;
import br.com.saxes.suite.writer.db.DBWriter;
import br.com.saxes.suite.writer.txt.DelimitedTXTWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter implements Runnable {

    static final Logger log = Logger.getLogger( Converter.class.getName() );

    private Project project;

    private Reader reader;
    private Writer writer;

    private HashMap<String, Pattern> charConversion;
	
	private TreeSchema finished;

    public Converter(Project project) {
        log.info("Iniciando conversor...");

        this.project = project;
		
		this.finished = new TreeSchema() {
			@Override
			protected TreeNode getSpecMappedTreeNodes(TreeNode node) {
				return null;
			}
		};

        TreeSchema _source = project.getSourceTreeSchema();
        if( _source instanceof DBTreeSchema ) {
            DBTreeSchema _dbTreeSchema = (DBTreeSchema) _source;
            reader = new DBReader( _dbTreeSchema, finished );
        } else if( _source instanceof FixedTXTTreeSchema ) {
            FixedTXTTreeSchema _txtTreeSchema = (FixedTXTTreeSchema) _source;
            reader = new FixedTXTReader( _txtTreeSchema, finished );
        }

        TreeSchema _target = project.getTargetTreeSchema();
        if( _target instanceof DelimitedTXTTreeSchema ) {
            DelimitedTXTTreeSchema _txtTreeSceSchema = (DelimitedTXTTreeSchema) _target;
            writer = new DelimitedTXTWriter(_txtTreeSceSchema, finished);
        } else if( _target instanceof DBTreeSchema ) {
            DBTreeSchema _dbTreeSchema = (DBTreeSchema) _target;
            writer = new DBWriter( _dbTreeSchema, finished );
        }

        Map<TextTreeNode, String> _charConversion = project.getCharConversion();
        charConversion = new HashMap<String, Pattern>( _charConversion.size() );
        for( Map.Entry<TextTreeNode, String> _buf : _charConversion.entrySet() ) {
            String _regex = "(";

            String _chars = _buf.getValue();
            for( int _x = 0; _x < _chars.length(); _x++ ) {
                _regex = _regex.concat( String.valueOf(_chars.charAt(_x)) );
            }
            _regex = _regex.concat( ")" );

            Pattern _patter = Pattern.compile( _regex );
            charConversion.put( _buf.getKey().getId(), _patter );
        }
    }

    @Override
    public void run() {
        Thread _tReader = new Thread(reader, "Reader " + reader.getClass().getSimpleName());
        Thread _tWriter = new Thread(writer, "Writer " + writer.getClass().getSimpleName());

        _tReader.start();
        _tWriter.start();

		TreeSchema _sourceSchema = null;
		try {
			while ( (_sourceSchema = reader.next()) != finished ) {

				TextTreeNode[] _sourceMappings = _sourceSchema.getMappedTreeNodes();

				TreeSchema _targetSchema = writer.borrowTreeSchema();

				for( TextTreeNode _sourceMapped : _sourceMappings ) {
					String targetID = project.getMappedTargetID( _sourceMapped.getId() );

					TextTreeNode[] tn = _targetSchema.getMappedTreeNodes();
					for( TextTreeNode temp : tn ) {
						if( targetID.equals(temp.getId()) ) {
							String _value = _sourceMapped.getValue();

							Pattern _pattern = charConversion.get( _sourceMapped.getId() );
							if( _pattern != null ) {
								Matcher _matcher = _pattern.matcher( _value );
								_value = _matcher.replaceAll(" ");
							}

							temp.setValue( _value );
							break;
						}
					}
				}

				writer.add( _targetSchema );
				reader.returnTreeSchema( _sourceSchema );
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				System.out.println("terminou!");
				writer.setFinished();
				_tWriter.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
    }

}
