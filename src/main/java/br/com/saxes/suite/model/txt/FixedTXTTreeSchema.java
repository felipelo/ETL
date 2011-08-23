package br.com.saxes.suite.model.txt;

/**
 *
 * @author felipe
 */
public class FixedTXTTreeSchema extends TXTTreeSchema {

    private int headerLines;
    private String newLine;

    public FixedTXTTreeSchema() {
        super(FileModelStrategy.FIXED_LENGHT);
        headerLines = 0;
        newLine = "\n";
    }

    public int getHeaderLines() { return headerLines; }
    public void setHeaderLines(int headerLines) { this.headerLines = headerLines; }

    public String getNewLine() { return newLine; }
    public void setNewLine(String newLine) { this.newLine = newLine; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FixedTXTTreeSchema clone = new FixedTXTTreeSchema();

        clone( clone );

        return clone;
    }

    protected void clone( FixedTXTTreeSchema treeSchema ) throws CloneNotSupportedException {
        super.clone( treeSchema );
        treeSchema.setHeaderLines(headerLines);
        treeSchema.setNewLine(newLine);
    }

}
