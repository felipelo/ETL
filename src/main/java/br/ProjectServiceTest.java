package br;

import br.com.saxes.suite.converter.Converter;
import br.com.saxes.suite.model.*;
import br.com.saxes.suite.model.db.*;
import br.com.saxes.suite.model.txt.*;
import br.com.saxes.suite.project.ProjectService;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

public class ProjectServiceTest {

    static int count = 0;

    public static Project createTXT2DBProject() {
        FixedTextTreeNode firstTreeNode = new FixedTextTreeNode();
        firstTreeNode.setId(String.valueOf(++count));
        firstTreeNode.setName("Codigo");
        firstTreeNode.setDescription("");
        firstTreeNode.setLenght(15);

        FixedTextTreeNode secTreeNode = new FixedTextTreeNode();
        secTreeNode.setId(String.valueOf(++count));
        secTreeNode.setName("item");
        secTreeNode.setDescription("");
        secTreeNode.setLenght(50);

        FixedTextTreeNode thriTreeNode = new FixedTextTreeNode();
        thriTreeNode.setId(String.valueOf(++count));
        thriTreeNode.setName("grupo");
        thriTreeNode.setDescription("");
        thriTreeNode.setLenght(90);

        FixedTextTreeNode fourthTreeNode = new FixedTextTreeNode();
        fourthTreeNode.setId(String.valueOf(++count));
        fourthTreeNode.setName("classe");
        fourthTreeNode.setDescription("");
        fourthTreeNode.setLenght(2);

        LineTreeNode lineTreeNode = new LineTreeNode();
        lineTreeNode.setId(String.valueOf(++count));
        lineTreeNode.setName("line_1");
        lineTreeNode.setDescription("Line description");
        lineTreeNode.addChild(firstTreeNode);
        lineTreeNode.addChild(secTreeNode);
        lineTreeNode.addChild(thriTreeNode);
        lineTreeNode.addChild(fourthTreeNode);

        //representa o file
        FileRef fileRef = new FileRef();
        fileRef.setFilePath("Produtos_materiais20100830142812.txt");

        FixedTXTTreeSchema txtTreeSchema = new FixedTXTTreeSchema();
        txtTreeSchema.setName("txt um dois tres");
        txtTreeSchema.setFileRef(fileRef);
        txtTreeSchema.setRoot(lineTreeNode);
        txtTreeSchema.setHeaderLines(1);
        txtTreeSchema.setNewLine("\r\n");


        //target schema
        TextTreeNode oneTreeNode = new TextTreeNode();
        oneTreeNode.setId(String.valueOf(++count));
        oneTreeNode.setName("codigo");
        oneTreeNode.setDescription("");

        TextTreeNode twoTreeNode = new TextTreeNode();
        twoTreeNode.setId(String.valueOf(++count));
        twoTreeNode.setName("item");
        twoTreeNode.setDescription("");

        TextTreeNode threeTreeNode = new TextTreeNode();
        threeTreeNode.setId(String.valueOf(++count));
        threeTreeNode.setName("grupo");
        threeTreeNode.setDescription("");

        TextTreeNode fourTreeNode = new TextTreeNode();
        fourTreeNode.setId(String.valueOf(++count));
        fourTreeNode.setName("classe");
        fourTreeNode.setDescription("");


        //representa a tabela
        TableTreeNode projetoTbl = new TableTreeNode();
        projetoTbl.setId(String.valueOf(++count));
        projetoTbl.setName("produto");
        projetoTbl.setDescription("");
        projetoTbl.addChild(oneTreeNode);
        projetoTbl.addChild(twoTreeNode);
        projetoTbl.addChild(threeTreeNode);
        projetoTbl.addChild(fourTreeNode);

        //connection
        ConnectionWrapper conn = new ConnectionWrapper();
        conn.setName("MySQL Root");
        conn.setConnURL("jdbc:mysql://localhost:3306/teste");
        conn.setUser("root");
        conn.setPassword("root");
        conn.setDriver("com.mysql.jdbc.Driver");

        WriteProperties wProps = new WriteProperties();
        wProps.setAction( WriteProperties.INSERT);

        //source schema
        DBTreeSchema dbTreeSchema = new DBTreeSchema();
        dbTreeSchema.setName("db um dois tres");
        dbTreeSchema.setConn(conn);
        dbTreeSchema.setRoot(projetoTbl);
        dbTreeSchema.setWriteProps(wProps);



        //project
        Project project = new Project();
        project.setName("projUm");
        project.setVersion("1.0");
        project.setCreation( new Date() );
        project.setLastUpdate( new Date() );
        project.setAuthor("Felipe A. Lorenz");
        project.setDescription("Test Project");
        project.setClient("Personal");

        project.setSourceTreeSchema(txtTreeSchema);
        project.setTargetTreeSchema(dbTreeSchema);

        project.addMapping(firstTreeNode, oneTreeNode, "");
        project.addMapping(secTreeNode, twoTreeNode, "");
        project.addMapping(thriTreeNode, threeTreeNode, "");
        project.addMapping(fourthTreeNode, fourTreeNode, "");

        project.addCharConversion( firstTreeNode, '0');

        return project;
    }

    public static Project createDB2TXTProject() {
        //representa o campo da tabela
        TextTreeNode colTreeNode = new TextTreeNode();
        colTreeNode.setId(String.valueOf(++count));
        colTreeNode.setName("codigo");
        colTreeNode.setDescription("");

        //representa o campo da tabela
        TextTreeNode dateColTreeNode = new TextTreeNode();
        dateColTreeNode.setId(String.valueOf(++count));
        dateColTreeNode.setName("item");
        dateColTreeNode.setDescription("");

        //representa o campo da tabela
        TextTreeNode numColTreeNode = new TextTreeNode();
        numColTreeNode.setId(String.valueOf(++count));
        numColTreeNode.setName("grupo");
        numColTreeNode.setDescription("");

        //representa o campo da tabela
        TextTreeNode classeColTreeNode = new TextTreeNode();
        classeColTreeNode.setId(String.valueOf(++count));
        classeColTreeNode.setName("classe");
        classeColTreeNode.setDescription("");

        //representa a tabela
        TableTreeNode atividadeTbl = new TableTreeNode();
        atividadeTbl.setId(String.valueOf(++count));
        atividadeTbl.setName("produto");
        atividadeTbl.setDescription("");
        atividadeTbl.addChild(colTreeNode);
        atividadeTbl.addChild(dateColTreeNode);
        atividadeTbl.addChild(numColTreeNode);
        atividadeTbl.addChild(classeColTreeNode);

//        //representa o campo da tabela
//        NumericTreeNode codProjColumn = new NumericTreeNode();
//        codProjColumn.setId(String.valueOf(++count));
//        codProjColumn.setName("codigo");
//        codProjColumn.setDescription("");
//
//        //representa o campo da tabela
//        TextTreeNode descProjColumn = new TextTreeNode();
//        descProjColumn.setId(String.valueOf(++count));
//        descProjColumn.setName("descricao");
//        descProjColumn.setDescription("");
//
//        FKTreeNode fk = new FKTreeNode();
//        fk.setId(String.valueOf(++count));
//        fk.setName("");
//        fk.setDescription("");
//        fk.setLocalColumn(codProjColumn);
//        fk.setForeignTable(atividadeTbl);
//        fk.setForeignColumn(classeColTreeNode);
//        fk.setJoinStrategy(JoinStrategy.INNER_JOIN);
//
//        //representa a tabela
//        TableTreeNode projetoTbl = new TableTreeNode();
//        projetoTbl.setId(String.valueOf(++count));
//        projetoTbl.setName("projeto");
//        projetoTbl.setDescription("");
//        projetoTbl.addChild(codProjColumn);
//        projetoTbl.addChild(descProjColumn);
//        projetoTbl.addChild(fk);

        //connection
        ConnectionWrapper conn = new ConnectionWrapper();
        conn.setName("MySQL Root");
        conn.setConnURL("jdbc:mysql://localhost:3306/teste");
        conn.setUser("root");
        conn.setPassword("root");
        conn.setDriver("com.mysql.jdbc.Driver");

        //source schema
        DBTreeSchema dbTreeSchema = new DBTreeSchema();
        dbTreeSchema.setName("db um dois tres");
        dbTreeSchema.setConn(conn);
        dbTreeSchema.setRoot(atividadeTbl);
        
        //representa campo
        TextTreeNode fieldTreeNode = new TextTreeNode();
        fieldTreeNode.setId(String.valueOf(++count));
        fieldTreeNode.setName("field_1");
        fieldTreeNode.setDescription("Field description");

        //representa campo
        TextTreeNode dateFieldTreeNode = new TextTreeNode();
        dateFieldTreeNode.setId(String.valueOf(++count));
        dateFieldTreeNode.setName("field_2");
        dateFieldTreeNode.setDescription("Field description");

        //representa campo
        TextTreeNode numFieldTreeNode = new TextTreeNode();
        numFieldTreeNode.setId(String.valueOf(++count));
        numFieldTreeNode.setName("field_1");
        numFieldTreeNode.setDescription("Field description");

        //representa campo
        TextTreeNode fieldTreeNode1 = new TextTreeNode();
        fieldTreeNode1.setId(String.valueOf(++count));
        fieldTreeNode1.setName("field_1");
        fieldTreeNode1.setDescription("Field description");

        //representa linha
        LineTreeNode lineTreeNode = new LineTreeNode();
        lineTreeNode.setId(String.valueOf(++count));
        lineTreeNode.setName("line_1");
        lineTreeNode.setDescription("Line description");
        lineTreeNode.addChild(fieldTreeNode);
        lineTreeNode.addChild(dateFieldTreeNode);
        lineTreeNode.addChild(numFieldTreeNode);
        lineTreeNode.addChild(fieldTreeNode1);

        //representa o file
        FileRef fileRef = new FileRef();
        fileRef.setFilePath("sadas.csv");

        //target schema
        DelimitedTXTTreeSchema txtTreeSchema = new DelimitedTXTTreeSchema();
        txtTreeSchema.setName("txt um dois tres");
        txtTreeSchema.setFileRef(fileRef);
        txtTreeSchema.setRoot(lineTreeNode);
        txtTreeSchema.setColumnDelimiter(';');
        txtTreeSchema.setFieldQualifier("\"");
        txtTreeSchema.setLineDelimiter('\n');

        //project
        Project project = new Project();
        project.setName("projUm");
        project.setVersion("1.0");
        project.setCreation( new Date() );
        project.setLastUpdate( new Date() );
        project.setAuthor("Felipe A. Lorenz");
        project.setDescription("Test Project");
        project.setClient("Personal");
        
        project.addMapping(colTreeNode, fieldTreeNode, "unico mapeamento");
        project.addMapping(dateColTreeNode, dateFieldTreeNode, "segundo mapeamento");
        project.addMapping(numColTreeNode, numFieldTreeNode, "terceiro mapeamento");
        project.addMapping(classeColTreeNode, fieldTreeNode1, "terceiro mapeamento");

        project.setSourceTreeSchema(dbTreeSchema);
        project.setTargetTreeSchema(txtTreeSchema);

        return project;
    }

    public void testSaveProject() throws JAXBException, FileNotFoundException {
        ProjectService instancee = new ProjectService();

//        System.out.println("saveProject");
//        Project _project = createProject();
//        instancee.saveProject(_project);
//        System.exit(0);

        
        Project p2 = instancee.loadProject( new File("test.xml").getAbsolutePath() );


        System.out.println("run");

        System.setProperty("saxessuite.systemDatePattern", "yyyy-MM-dd HH:mm:ss");
        System.setProperty("saxessuite.systemNumPattern", "#.00000000");

        long time = System.currentTimeMillis();
        Project project = p2;

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

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        new ProjectServiceTest().testSaveProject();
    }
}
