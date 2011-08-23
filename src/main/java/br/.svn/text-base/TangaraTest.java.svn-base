package br;

import br.com.saxes.suite.converter.Converter;
import br.com.saxes.suite.model.Project;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.db.ConnectionWrapper;
import br.com.saxes.suite.model.db.DBTreeSchema;
import br.com.saxes.suite.model.db.TableTreeNode;
import br.com.saxes.suite.model.db.UpdateCondition;
import br.com.saxes.suite.model.db.WriteProperties;
import br.com.saxes.suite.model.txt.FileRef;
import br.com.saxes.suite.model.txt.FixedTXTTreeSchema;
import br.com.saxes.suite.model.txt.FixedTextTreeNode;
import br.com.saxes.suite.model.txt.LineTreeNode;
import br.com.saxes.suite.project.ProjectService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author felipe
 */
public class TangaraTest {

    int id = 0;

    public Project createProject() {
        FixedTextTreeNode _cnpj = new FixedTextTreeNode();
        _cnpj.setId( String.valueOf(id++) );
        _cnpj.setName( "CNPJ" );
        _cnpj.setLenght( 14 );

        FixedTextTreeNode _nome = new FixedTextTreeNode();
        _nome.setId( String.valueOf(id++) );
        _nome.setName( "Nome" );
        _nome.setLenght( 45 );

        FixedTextTreeNode _razaoSocial = new FixedTextTreeNode();
        _razaoSocial.setId( String.valueOf(id++) );
        _razaoSocial.setName( "Razão Social" );
        _razaoSocial.setLenght( 45 );

        FixedTextTreeNode _categoria = new FixedTextTreeNode();
        _categoria.setId( String.valueOf(id++) );
        _categoria.setName( "Categoria" );
        _categoria.setLenght( 2 );

        FixedTextTreeNode _segmento = new FixedTextTreeNode();
        _segmento.setId( String.valueOf(id++) );
        _segmento.setName( "Segmento" );
        _segmento.setLenght( 55 );

        FixedTextTreeNode _segmentoEconomico = new FixedTextTreeNode();
        _segmentoEconomico.setId( String.valueOf(id++) );
        _segmentoEconomico.setName( "Segmento Economico" );
        _segmentoEconomico.setLenght( 70 );

        FixedTextTreeNode _situacaoCliente = new FixedTextTreeNode();
        _situacaoCliente.setId( String.valueOf(id++) );
        _situacaoCliente.setName( "Situação Cliente" );
        _situacaoCliente.setLenght( 30 );

        FixedTextTreeNode _carteira = new FixedTextTreeNode();
        _carteira.setId( String.valueOf(id++) );
        _carteira.setName( "Carteira" );
        _carteira.setLenght( 40 );

        FixedTextTreeNode _vendedorCliente = new FixedTextTreeNode();
        _vendedorCliente.setId( String.valueOf(id++) );
        _vendedorCliente.setName( "Vendedor Cliente" );
        _vendedorCliente.setLenght( 40 );

        FixedTextTreeNode _cidade = new FixedTextTreeNode();
        _cidade.setId( String.valueOf(id++) );
        _cidade.setName( "Cidade" );
        _cidade.setLenght( 20 );

        FixedTextTreeNode _uf = new FixedTextTreeNode();
        _uf.setId( String.valueOf(id++) );
        _uf.setName( "UF" );
        _uf.setLenght( 2 );

        LineTreeNode _line = new LineTreeNode();
        _line.setId( String.valueOf(id++) );
        _line.setName( "root" );
        _line.addChild( _cnpj );
        _line.addChild( _nome );
        _line.addChild( _razaoSocial );
        _line.addChild( _categoria );
        _line.addChild( _segmento );
        _line.addChild( _segmentoEconomico );
        _line.addChild( _situacaoCliente );
        _line.addChild( _carteira );
        _line.addChild( _vendedorCliente );
        _line.addChild( _cidade );
        _line.addChild( _uf );

        FileRef fileRef = new FileRef();
        fileRef.setFilePath("C:\\Users\\felipe\\Desktop\\tangara\\clientes_2.txt");

        FixedTXTTreeSchema _fixedTXTTreeSchema = new FixedTXTTreeSchema();
        _fixedTXTTreeSchema.setName( "Clientes" );
        _fixedTXTTreeSchema.setFileRef( fileRef );
        _fixedTXTTreeSchema.setRoot( _line );
        _fixedTXTTreeSchema.setHeaderLines( 0 );
        _fixedTXTTreeSchema.setNewLine( "\r\n" );

        //target
        TextTreeNode _dCnpj = new TextTreeNode();
        _dCnpj.setId( String.valueOf(id++) );
        _dCnpj.setName( "f59" );

        TextTreeNode _dCarteira = new TextTreeNode();
        _dCarteira.setId( String.valueOf(id++) );
        _dCarteira.setName( "f100" );

        TextTreeNode _dCategoria = new TextTreeNode();
        _dCategoria.setId( String.valueOf(id++) );
        _dCategoria.setName( "f75" );

        TextTreeNode _dCidade = new TextTreeNode();
        _dCidade.setId( String.valueOf(id++) );
        _dCidade.setName( "f76" );

        TextTreeNode _dNome = new TextTreeNode();
        _dNome.setId( String.valueOf(id++) );
        _dNome.setName( "f2" );

        TextTreeNode _dRazaoSocial = new TextTreeNode();
        _dRazaoSocial.setId( String.valueOf(id++) );
        _dRazaoSocial.setName( "f28" );

        TextTreeNode _dSegmento = new TextTreeNode();
        _dSegmento.setId( String.valueOf(id++) );
        _dSegmento.setName( "f81" );

        TextTreeNode _dSegmentoEconomico = new TextTreeNode();
        _dSegmentoEconomico.setId( String.valueOf(id++) );
        _dSegmentoEconomico.setName( "f67" );

        TextTreeNode _dSituacaoCliente = new TextTreeNode();
        _dSituacaoCliente.setId( String.valueOf(id++) );
        _dSituacaoCliente.setName( "f69" );

        TextTreeNode _dUF = new TextTreeNode();
        _dUF.setId( String.valueOf(id++) );
        _dUF.setName( "f60" );

        TextTreeNode _dVendedorCliente = new TextTreeNode();
        _dVendedorCliente.setId( String.valueOf(id++) );
        _dVendedorCliente.setName( "f10" );

        UpdateCondition _updateCond = new UpdateCondition();
        _updateCond.setColumn( _dCnpj );
        _updateCond.setOperator( UpdateCondition.EQUAL );

        TableTreeNode _mod0002 = new TableTreeNode();
        _mod0002.setId( String.valueOf(id++) );
        _mod0002.setName( "mod0002" );
        _mod0002.addUpdateCondition( _updateCond );
        _mod0002.addChild( _dCnpj );
        _mod0002.addChild( _dVendedorCliente );
        _mod0002.addChild( _dUF );
        _mod0002.addChild( _dSituacaoCliente );
        _mod0002.addChild( _dSegmentoEconomico );
        _mod0002.addChild( _dSegmento );
        _mod0002.addChild( _dRazaoSocial );
        _mod0002.addChild( _dNome );
        _mod0002.addChild( _dCidade );
        _mod0002.addChild( _dCategoria );
        _mod0002.addChild( _dCarteira );

        ConnectionWrapper _conn = new ConnectionWrapper();
        _conn.setName( "Tangara" );
        _conn.setConnURL( "jdbc:postgresql://192.168.0.11:5432/tangara" );
        _conn.setUser( "postgres" );
        _conn.setPassword( "root" );
        _conn.setDriver( "org.postgresql.Driver" );

        WriteProperties _writeProps = new WriteProperties();
        _writeProps.setAction( WriteProperties.UPDATE );

        DBTreeSchema _dbTreeSchema = new DBTreeSchema();
        _dbTreeSchema.setName( "MOD0002" );
        _dbTreeSchema.setConn( _conn );
        _dbTreeSchema.setRoot( _mod0002 );
        _dbTreeSchema.setWriteProps( _writeProps );

        //projetct
        Project _project = new Project();
        _project.setName( "Update Tangara" );

        _project.setSourceTreeSchema( _fixedTXTTreeSchema );
        _project.setTargetTreeSchema( _dbTreeSchema );

        _project.addMapping( _cnpj, _dCnpj, null);
        _project.addMapping( _vendedorCliente, _dVendedorCliente, null);
        _project.addMapping( _uf, _dUF, null);
        _project.addMapping( _situacaoCliente, _dSituacaoCliente, null);
        _project.addMapping( _segmentoEconomico, _dSegmentoEconomico, null);
        _project.addMapping( _segmento, _dSegmento, null);
        _project.addMapping( _razaoSocial, _dRazaoSocial, null);
        _project.addMapping( _nome, _dNome, null);
        _project.addMapping( _cidade, _dCidade, null);
        _project.addMapping( _carteira, _dCarteira, null);
        _project.addMapping( _categoria, _dCategoria, null);

        return _project;
    }

    public void executeProject() {
        System.setProperty("saxessuite.systemDatePattern", "yyyy-MM-dd HH:mm:ss");
        System.setProperty("saxessuite.systemNumPattern", "#.00000000");

        long time = System.currentTimeMillis();
        Project project = createProject();

        Converter instance = new Converter(project);
        Thread t = new Thread(instance, "Converter");
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(System.currentTimeMillis()-time);
    }

    public void saveProject() throws JAXBException {
        Project project = createProject();

        ProjectService service = new ProjectService();
        service.saveProject(project);
    }

    public static void main(String[] args) throws JAXBException {
        TangaraTest t = new TangaraTest();
//        t.executeProject();
        t.saveProject();
    }

}
