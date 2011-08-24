package br.com.saxes.suite.writer.db;

import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.db.ConnectionWrapper;
import br.com.saxes.suite.model.db.DBTreeSchema;
import br.com.saxes.suite.model.db.TableTreeNode;
import br.com.saxes.suite.model.db.UpdateCondition;
import br.com.saxes.suite.model.db.WriteProperties;
import br.com.saxes.suite.reader.ReaderInitException;
import br.com.saxes.suite.writer.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author felipe
 */
public class DBWriter extends Writer {

    private Map<String, PreparedStatement> pStmts;
    private List<TableTreeNode> tableOrder;
    private Connection conn;

    public DBWriter( DBTreeSchema treeSchema ) throws ReaderInitException {
        super(treeSchema);

        //open connection
        try {
            ConnectionWrapper connWrapper = treeSchema.getConn();

            log.info("DB Driver: ".concat(connWrapper.getDriver()));
            log.info("DB URL: ".concat(connWrapper.getConnURL()));
            log.info("DB Usuário: ".concat(connWrapper.getUser()));

            Class.forName(connWrapper.getDriver());
            conn = DriverManager.getConnection(connWrapper.getConnURL(), connWrapper.getUser(), connWrapper.getPassword());
            conn.setAutoCommit( false );
        } catch (SQLException ex) {
            throw new ReaderInitException("Can't open data connection.", ex);
        } catch (ClassNotFoundException ex) {
            throw new ReaderInitException("Driver's class can't be located.", ex);
        }

        try {
            WriteProperties props = treeSchema.getWriteProps();

            TableTreeNode _tables[] = treeSchema.getUsedTables();
            pStmts = new HashMap<String, PreparedStatement>(_tables.length);
            tableOrder = new ArrayList<TableTreeNode>( _tables.length );

            for (TableTreeNode _table : _tables) {
                TextTreeNode _columns[] = _table.getMappedColumns();
                StringBuilder sql = new StringBuilder();
                if (props.getAction() == WriteProperties.INSERT) {
                    sql.append("INSERT INTO ").append(_table.getName()).append(" (");

                    int _x = 0;
                    int _size = _columns.length - 1;
                    StringBuilder _values = new StringBuilder("VALUES (");
                    for (_x = 0; _x < _size;) {
                        TextTreeNode _column = _columns[_x++];
                        sql.append(_column.getName()).append(", ");
                        _values.append("?, ");
                    }
                    TextTreeNode _column = _columns[_x++];
                    sql.append(_column.getName()).append(") ");
                    _values.append("?)");
                    sql.append(_values);
                } else if( props.getAction() == WriteProperties.UPDATE ) {
                    sql.append( "UPDATE " ).append( _table.getName() ).append( " SET " );

                    int _x = 0;
                    int _size = _columns.length - 1;
                    for (_x = 0; _x < _size;) {
                        TextTreeNode _column = _columns[_x++];
                        sql.append( _column.getName() ).append( " = ?, " );
                    }
                    TextTreeNode _column = _columns[_x++];
                    sql.append( _column.getName() ).append( " = ? " );

                    if( _table.getConditions().size() > 0 ) {
                        sql.append( "WHERE " );
                        for( _x = 0; _x < _table.getConditions().size(); _x++ ) {
                            UpdateCondition _updateCond = _table.getConditions().get( _x );
                            sql.append( _updateCond.getColumn().getName() )
                               .append( " " )
                               .append( _updateCond.getOperator() )
                               .append( " ?" );
                        }
                    }
                }
                System.out.println(sql.toString());
                PreparedStatement pStmt = conn.prepareStatement( sql.toString() );
                pStmts.put(_table.getId(), pStmt);

                //create a table order for insert/update
                tableOrder.add( _table );
            }
            Collections.reverse( tableOrder );

        } catch (SQLException ex) {
            throw new ReaderInitException("", ex);
        }
    }

    @Override
    public void run() {
        try {
            while( !buffer.isEmpty() || !finished ) {
                synchronized( buffer ) {
                    while( buffer.isEmpty() && !finished ) {
                        buffer.wait();
                    }
                }

                DBTreeSchema _treeSchema = (DBTreeSchema) buffer.remove(0);
                
                for( TableTreeNode _orderTable : tableOrder ) {
                    TableTreeNode _table = (TableTreeNode) _treeSchema.getTreeNodeByID( _orderTable.getId() );
                    PreparedStatement _pstmt = pStmts.get( _table.getId() );
                    _pstmt.clearParameters();

                    int _x = 0;
                    TextTreeNode _columns[] = _table.getMappedColumns();
                    for( _x = 0; _x < _columns.length; _x++ ) {
                        TextTreeNode _column = _columns[_x];
                        _pstmt.setString( _x+1, _column.getValue() );
                    }

                    /*
                     * update conditions... only will work with mapped nodes.
                     * If a node is not mapped and it is used as condition, the result will be
                     * like 'WHERE <node_name> = null'
                     */
                    if( _table.getConditions().size() > 0 ) {
                        for( int _y = _x; _y < (_table.getConditions().size()+_x); _y++ ) {
                            UpdateCondition _updateCond = _table.getConditions().get( _y-_x );
                            TextTreeNode _tn = (TextTreeNode) _treeSchema.getTreeNodeByID( _updateCond.getColumn().getId() );
                            _pstmt.setString( _y+1, _tn.getValue());
                        }
                    }

                    _pstmt.executeUpdate();
                }
                
                treeSchemaPool.returnObject( _treeSchema );
            }

            conn.commit();
            
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

}
