package br.com.saxes.suite.model.txt;

import br.com.saxes.suite.model.TreeNode;
import br.com.saxes.suite.model.TreeSchema;

public class TXTTreeSchema extends TreeSchema {

    private FileRef fileRef;
    private String fieldQualifier;
    private FileModelStrategy modelStrategy;

    public TXTTreeSchema() {
        this( FileModelStrategy.UNDEFINED );
    }

    public TXTTreeSchema( FileModelStrategy modelStrategy ) {
        super();
        this.modelStrategy = modelStrategy;
        fieldQualifier = "";
    }

    @Override
    protected TreeNode getSpecMappedTreeNodes( TreeNode node ) {
        return node;
    }

    public FileRef getFileRef() { return fileRef; }
    public void setFileRef(FileRef fileRef) { this.fileRef = fileRef; }

    public FileModelStrategy getModelStrategy() { return modelStrategy; }
    public void setModelStrategy(FileModelStrategy modelStrategy) { this.modelStrategy = modelStrategy; }

    public String getFieldQualifier() { return fieldQualifier; }
    public void setFieldQualifier(String fieldQualifier) { this.fieldQualifier = fieldQualifier; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TXTTreeSchema clone = new TXTTreeSchema( FileModelStrategy.UNDEFINED );

        clone( clone );

        return clone;
    }

    protected void clone( TXTTreeSchema treeSchema ) throws CloneNotSupportedException {
        super.clone( treeSchema );
        treeSchema.setFileRef((FileRef) fileRef.clone());
        treeSchema.setModelStrategy(modelStrategy);
        treeSchema.setFieldQualifier(fieldQualifier);
    }

}
