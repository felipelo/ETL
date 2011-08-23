package br.com.saxes.suite.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represent a migration project, with basic information about it.
 *
 * @author Felipe
 */
@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Project {

    private String name;
    private String version;
    private Date creation;
    private Date lastUpdate;
    private String author;
    private String description;
    private String client;

    /**List with all mappings between source and target*/
    @XmlElementWrapper(name="maps")
    private ArrayList<Mapping> mappings;

    /**Represents the source schema*/
    private TreeSchema sourceTreeSchema;
    /**Represents the target schema*/
    private TreeSchema targetTreeSchema;

    /**Represent the char to remove for white space*/
    private HashMap<TextTreeNode, String> charConversion;

    public Project() {
        mappings = new ArrayList<Mapping>();
        charConversion = new HashMap<TextTreeNode, String>();
    }

    public void addCharConversion( TextTreeNode treeNode, char charToRemove ) {
        String _seq = charConversion.get( treeNode );

        _seq = (_seq == null) ? "" : _seq;

        _seq = _seq.concat( String.valueOf(charToRemove) );

        charConversion.put( treeNode, _seq );
    }

    /**
     * Add a mapping to the project
     *
     * @param sourceTreeNode
     * @param targetTreeNode
     * @param description
     */
    public void addMapping( TextTreeNode sourceTreeNode, TextTreeNode targetTreeNode, String description ) {
        Mapping mapping = new Mapping();

        sourceTreeNode.setMapped(true);
        targetTreeNode.setMapped(true);
        
        mapping.setSourceTreeNode(sourceTreeNode);
        mapping.setTargetTreeNode(targetTreeNode);
        
        mapping.setDescription(description);

        mappings.add(mapping);
    }

    /**
     * Return an target node from the mapping list using the source ID
     *
     * @param sourceID
     * @return An ID from the target node
     */
    public String getMappedTargetID( String sourceID ) {
        for( Mapping map : mappings ) {
            if( sourceID.equals(map.getSourceTreeNode().getId()) ) {
                return map.getTargetTreeNode().getId();
            }
        }
        return null;
    }

    /**
     * Returns true only if the TreeNode are mapping.
     *
     * @param treeNode The TreeNode to verify
     * @return
     */
    public boolean isMapped(TextTreeNode treeNode) {
        for( Mapping map : mappings ) {
            if(map.containTreeNode(treeNode))
                return true;
        }
        return false;
    }

    /*
     * Getters & Setters methods
     */

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client;}

    public Date getCreation() { return creation; }
    public void setCreation(Date creation) { this.creation = creation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    /**
     * This method should not use with the propose to add or remove elements from this list.
     * If you need to work with the mapping list, you should use the MappingService class.
     * @see MappingService
     * @return
     */
    public ArrayList<Mapping>  getMappings() { return new ArrayList<Mapping>(mappings); }
    public void setMappings(ArrayList<Mapping> mappings) { this.mappings = mappings; }

    public TreeSchema getSourceTreeSchema() { return sourceTreeSchema; }
    public void setSourceTreeSchema(TreeSchema sourceTreeSchema) { this.sourceTreeSchema = sourceTreeSchema; }

    public TreeSchema getTargetTreeSchema() { return targetTreeSchema; }
    public void setTargetTreeSchema(TreeSchema targetTreeSchema) { this.targetTreeSchema = targetTreeSchema; }

    public HashMap<TextTreeNode, String> getCharConversion() { return new HashMap<TextTreeNode, String>(charConversion); }
    public void setCharConversion(HashMap<TextTreeNode, String> charConversion) { this.charConversion = charConversion; }
}
