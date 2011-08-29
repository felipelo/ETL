package br;

import br.com.saxes.suite.converter.Converter;
import br.com.saxes.suite.model.Project;
import br.com.saxes.suite.model.txt.TXTTreeSchema;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConverterTest {

    public static void testDB2TXT() {
        System.out.println("run");

        System.setProperty("saxessuite.systemDatePattern", "yyyy-MM-dd HH:mm:ss");
        System.setProperty("saxessuite.systemNumPattern", "#.00000000");

        long time = System.currentTimeMillis();
        Project project = ProjectServiceTest.createDB2TXTProject();

        Converter instance = new Converter(project);
        Thread t = new Thread(instance, "Converter");
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(System.currentTimeMillis()-time);

        TXTTreeSchema s = (TXTTreeSchema) project.getTargetTreeSchema();
        try {
            Desktop.getDesktop().open(new File(s.getFileRef().getFilePath()));
        } catch (IOException ex) {
            Logger.getLogger(ConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void testTXT2DB() {
        System.out.println("run");

        System.setProperty("saxessuite.systemDatePattern", "yyyy-MM-dd HH:mm:ss");
        System.setProperty("saxessuite.systemNumPattern", "#.00000000");

        long time = System.currentTimeMillis();
        Project project = ProjectServiceTest.createTXT2DBProject();

        Converter instance = new Converter(project);
        Thread t = new Thread(instance, "Converter");
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-time);
    }

    public static void main(String[] args) {
        new ConverterTest().testDB2TXT();
//        new ConverterTest().testTXT2DB();
    }

}
