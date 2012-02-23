package br.com.saxes.suite.reader.db;

import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.model.db.*;
import br.com.saxes.suite.reader.Reader;
import br.com.saxes.suite.reader.ReaderInitException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

public class DBReader extends Reader {

    private DBTreeSchema dbTreeSchema;

    private final ResultSet rs;

    public DBReader( DBTreeSchema dbTreeSchema, TreeSchema finished ) throws ReaderInitException {
        super(dbTreeSchema, finished);

        this.dbTreeSchema = dbTreeSchema;

        //compile SQL
        TableTreeNode rootTables = dbTreeSchema.getRootTable();
        TextTreeNode[] mappedColumns = dbTreeSchema.getMappedTreeNodes();

        StringBuilder sql = new StringBuilder("SELECT ");

        //columns
        int x = 0;
        int size = mappedColumns.length-1;
        for( x = 0; x < size; ) {
            TreeNode column = mappedColumns[x++];
            sql.append( column.getParentTreeNode().getName() )
               .append(".")
               .append( column.getName() )
               .append(", ");
        }
        TreeNode column = mappedColumns[x];
        sql.append( column.getParentTreeNode().getName() )
               .append(".")
               .append( column.getName() )
               .append(" ");

        //tables
        sql.append("FROM ");
        sql.append( rootTables.getName() );
        sql.append(" ");
        mountTable(sql, rootTables);

        log.info("SQL Query: ".concat(sql.toString()));

        //open connection
        Connection conn = null;
        try {
            ConnectionWrapper connWrapper = dbTreeSchema.getConn();

            log.info("DB Driver: ".concat(connWrapper.getDriver()));
            log.info("DB URL: ".concat(connWrapper.getConnURL()));
            log.info("DB Usuário: ".concat(connWrapper.getUser()));

            Class.forName(connWrapper.getDriver());
            conn = DriverManager.getConnection(connWrapper.getConnURL(), connWrapper.getUser(), connWrapper.getPassword());
            conn.setReadOnly(true);
        } catch (SQLException ex) {
            throw new ReaderInitException("Can't open data connection.", ex);
        } catch (ClassNotFoundException ex) {
            throw new ReaderInitException("Driver's class can't be located.", ex);
        }
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery( sql.toString() );
        } catch (SQLException ex) {
            throw new ReaderInitException("", ex);
        }
    }

    @Override
    public void run() {
        try {
            while (rs.next()) {
                
                DBTreeSchema _register = (DBTreeSchema) treeSchemaPool.borrowObject();

                TextTreeNode[] _mappeds = _register.getMappedTreeNodes();
                for( TextTreeNode _column : _mappeds ) {
                    //for DB schema, the columns' parent should be a TableTreeNode, always!
                    String _c = _column.getParentTreeNode().getName();
                    _c = _c.concat(".").concat( _column.getName() );

                    String _value = null;
                    switch( _column.getValueType() ) {
                        case DATE:
                            Date _date = rs.getTimestamp( _c );
                            if( _date != null )
                                _value = systemDateFormat.format( _date );
                            break;
                        case TEXT:
                            _value = rs.getString(_c);
                            break;
                        case NUMERIC:
                            Double _decimal = rs.getDouble( _c );
                            if( _decimal != null )
                                _value = systemNumFormat.format( _decimal.doubleValue() );
                            break;
                    }
                    _column.setValue( _value );

//                    if( _value == null ) {
//                        System.out.println(_value);
//                    }

                    log.finest("Lendo dado: ".concat(_c).concat(": ").concat(_value));
                }
                
                buffer.put( _register );
            }
        } catch (Exception ex) {
            //should not throw any exception
            log.log(Level.SEVERE, "", ex);
        } finally {
            try {
                rs.close();
				setFinished();
            } catch (Exception ex) {}
        }
    }


    /**
     * Create de FROM sentence with INNER JOIN, ON AND...
     * @param sql
     * @param rootTable
     */
    private void mountTable(StringBuilder sql, TableTreeNode rootTable) {
        ArrayList<FKTreeNode> _childs = rootTable.getForeignKeys();
        for( FKTreeNode _fkTreeNode : _childs ) {
            sql.append( _fkTreeNode.getJoinStrategy().getJoinCommand() );
            sql.append( " " );
            sql.append( _fkTreeNode.getForeignTable().getName() );
            sql.append( " ON " );
            sql.append( rootTable.getName() )
               .append( "." )
               .append( _fkTreeNode.getLocalColumn().getName() );
            sql.append( " = " );
            sql.append( _fkTreeNode.getForeignTable().getName() )
               .append( "." )
               .append( _fkTreeNode.getForeignColumn().getName() );
            sql.append( " " );

            mountTable( sql, _fkTreeNode.getForeignTable() );
        }
    }

}
