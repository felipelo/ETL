package br.com.saxes.suite.project;

import br.com.saxes.suite.model.Mapping;
import br.com.saxes.suite.model.Project;
import br.com.saxes.suite.model.TextTreeNode;
import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ProjectService {

    private JAXBContext context;

    private HashMap<String, Project> projects;

    public ProjectService() {
        try {
            context = JAXBContext.newInstance(getClasses("br.com.saxes.suite.model"));
            projects = new HashMap<String, Project>(5);
        } catch (Exception ex) {
            //not suppose to throw any error
            ex.printStackTrace();
        }
    }

    public void saveProject(Project project)
            throws JAXBException {
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        m.marshal(project, System.out);
    }

    public Project loadProject( String projectPath ) 
            throws JAXBException, FileNotFoundException {
        Unmarshaller u = context.createUnmarshaller();

        Project project = (Project) u.unmarshal( new FileInputStream(projectPath) );

        ArrayList<Mapping> _maps = project.getMappings();
        for( Mapping _map : _maps ) {
            _map.getSourceTreeNode().setMapped(true);
            _map.getTargetTreeNode().setMapped(true);
        }

        /*
         *set-up some transients fields
         */
        //parent's childs
        TreeSchema treeSchema = project.getSourceTreeSchema();
        TreeNode root = treeSchema.getRoot();
        setChildParent(root);
        treeSchema = project.getTargetTreeSchema();
        root = treeSchema.getRoot();
        setChildParent(root);
        setMappedTreeNode(project, root);

        projects.put(projectPath, project);

        return project;
    }

    private void setChildParent( TreeNode parent ) {
        ArrayList<TreeNode> childs = parent.getChilds();
        for( TreeNode child : childs ) {
            child.setParentTreeNode(parent);
            setChildParent(child);
        }
    }

    private void setMappedTreeNode(Project project, TreeNode parent) {
        if( parent instanceof TextTreeNode ) {
            TextTreeNode _node = (TextTreeNode) parent;
            if( project.isMapped(_node) ){
                _node.setMapped(true);

                ArrayList<TreeNode> childs = _node.getChilds();
                for( TreeNode child : childs ) {
                    setMappedTreeNode(project, child);
                }
            }
        }
    }

    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
