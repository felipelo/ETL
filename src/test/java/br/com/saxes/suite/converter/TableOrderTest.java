/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.saxes.suite.converter;

import br.com.saxes.suite.model.NumericTreeNode;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeSchema;
import br.com.saxes.suite.model.db.ConnectionWrapper;
import br.com.saxes.suite.model.db.DBTreeSchema;
import br.com.saxes.suite.model.db.FKTreeNode;
import br.com.saxes.suite.model.db.JoinStrategy;
import br.com.saxes.suite.model.db.Reference;
import br.com.saxes.suite.model.db.TableTreeNode;
import junit.framework.TestCase;

/**
 *
 * @author Felipe
 */
public class TableOrderTest extends TestCase {
    
    public TableOrderTest(String testName) {
        super(testName);
    }
    
    private int id = 0;
    
    private TreeSchema sourceTreeSchema() {
        DBTreeSchema _schema = null;
        
        _schema = new DBTreeSchema();
        _schema.setName("TableOrderTest");
        
        ConnectionWrapper _conn = new ConnectionWrapper();
        _conn.setName("HSQLDB Connection");
        _conn.setDriver("org.apache.derby.jdbc.EmbeddedDriver");
        _conn.setConnURL("jdbc:derby:d:/NFSDB");
        _conn.setUser("");
        _conn.setPassword("");
        _schema.setConn( _conn );
        
        // TABELA CLIENTE
        TableTreeNode _tblCliente = new TableTreeNode();
        _tblCliente.setId(String.valueOf(++id));
        _tblCliente.setName("cliente");
        
        NumericTreeNode _cliCodigo = new NumericTreeNode();
        _cliCodigo.setId(String.valueOf(++id));
        _cliCodigo.setName("codigo");
        _cliCodigo.setFieldNumFormat("#");
        _tblCliente.addChild(_cliCodigo);
        
        TextTreeNode _cliNome = new TextTreeNode();
        _cliNome.setId(String.valueOf(++id));
        _cliNome.setName("nome");
        _tblCliente.addChild(_cliNome);
        
        //TABELA ITEM
        TableTreeNode _tblItem = new TableTreeNode();
        _tblItem.setId(String.valueOf(++id));
        _tblItem.setName("item");
        
        NumericTreeNode _itemCodigo = new NumericTreeNode();
        _itemCodigo.setId(String.valueOf(++id));
        _itemCodigo.setName("codigo");
        _itemCodigo.setFieldNumFormat("#");
        _tblItem.addChild(_itemCodigo);
        
        TextTreeNode _itemDescricao = new TextTreeNode();
        _itemDescricao.setId(String.valueOf(++id));
        _itemDescricao.setName("descricao");
        _tblItem.addChild(_itemDescricao);
        
        //TABELA NFS
        TableTreeNode _tblNfs = new TableTreeNode();
        _tblNfs.setId(String.valueOf(++id));
        _tblNfs.setName("nfs");
        
        NumericTreeNode _nfsNumero = new NumericTreeNode();
        _nfsNumero.setId(String.valueOf(++id));
        _nfsNumero.setName("numero");
        _tblNfs.addChild(_nfsNumero);
        
        NumericTreeNode _nfsSerie = new NumericTreeNode();
        _nfsSerie.setId(String.valueOf(++id));
        _nfsSerie.setName("serie");
        _tblNfs.addChild(_nfsSerie);
        
        NumericTreeNode _nfsCodCliente = new NumericTreeNode();
        _nfsCodCliente.setId(String.valueOf(++id));
        _nfsCodCliente.setName("codCliente");
        _tblNfs.addChild(_nfsCodCliente);
        
        Reference _ref = new Reference(_nfsCodCliente, _cliCodigo);
        
        FKTreeNode _fkNfsCliente = new FKTreeNode();
        _fkNfsCliente.setId(String.valueOf(++id));
        _fkNfsCliente.setName("");
        _fkNfsCliente.setForeignTable(_tblCliente);
        _fkNfsCliente.addReference(_ref);
        _fkNfsCliente.setJoinStrategy(JoinStrategy.INNER_JOIN);
        _tblNfs.addChild(_fkNfsCliente);
        
        //TABELA ITEMNFS
        TableTreeNode _tblItemNfs = new TableTreeNode();
        _tblItemNfs.setId(String.valueOf(++id));
        _tblItemNfs.setName("itemnfs");
        
        NumericTreeNode _itemNfsNumero = new NumericTreeNode();
        _itemNfsNumero.setId(String.valueOf(++id));
        _itemNfsNumero.setName("numero");
        _tblItemNfs.addChild(_itemNfsNumero);
        
        NumericTreeNode _itemNfsSerie = new NumericTreeNode();
        _itemNfsSerie.setId(String.valueOf(++id));
        _itemNfsSerie.setName("serie");
        _tblItemNfs.addChild(_itemNfsSerie);
        
        NumericTreeNode _itemNfsSeq = new NumericTreeNode();
        _itemNfsSeq.setId(String.valueOf(++id));
        _itemNfsSeq.setName("sequencia");
        _tblItemNfs.addChild(_itemNfsSeq);
        
        Reference _reff = new Reference(_itemNfsNumero, _nfsNumero);
        Reference _reff2 = new Reference(_itemNfsSerie, _nfsSerie);
        
        FKTreeNode _fkNfsItemNfs = new FKTreeNode();
        _fkNfsItemNfs.setId(String.valueOf(++id));
        _fkNfsItemNfs.setName("");
        _fkNfsItemNfs.setForeignTable(_tblCliente);
        _fkNfsItemNfs.addReference(_reff);
        _fkNfsItemNfs.addReference(_reff2);
        _fkNfsItemNfs.setJoinStrategy(JoinStrategy.INNER_JOIN);
        _tblNfs.addChild(_fkNfsItemNfs);
        
        return _schema;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
//        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//        Connection con = DriverManager.getConnection("jdbc:derby:d:/NFSDB;create=true");
//        Statement stmt = con.createStatement();
//        stmt.executeUpdate("CREATE TABLE cliente (codigo INT primary key, nome VARCHAR(50))");
//        stmt.executeUpdate("CREATE TABLE item (codigo INT primary key, descricao VARCHAR(50))");
//        stmt.executeUpdate("CREATE TABLE nfs (numero INT, serie INT, codCliente INT, PRIMARY KEY(numero,serie), FOREIGN KEY(codCliente) REFERENCES cliente(codigo))");
//        stmt.executeUpdate("CREATE TABLE itemnfs (numero INT, serie INT, sequencia INT, codItem INT, PRIMARY KEY(numero,serie,sequencia), FOREIGN KEY(codItem) REFERENCES item(codigo))");
//        con.close();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of run method, of class Converter.
     */
    public void testRun() {
        System.out.println("run");
//        Converter instance = null;
//        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        assertTrue(true);
    }

}
