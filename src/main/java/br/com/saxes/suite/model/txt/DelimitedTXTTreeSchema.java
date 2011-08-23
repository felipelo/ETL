package br.com.saxes.suite.model.txt;

/**
 *
 * @author felipe
 */
public class DelimitedTXTTreeSchema extends TXTTreeSchema {

    private char lineDelimiter;
    private char columnDelimiter;

    public DelimitedTXTTreeSchema() {
        super( FileModelStrategy.DELIMITED );
    }

    public char getColumnDelimiter() { return columnDelimiter; }
    public void setColumnDelimiter(char columnDelimiter) { this.columnDelimiter = columnDelimiter; }

    public char getLineDelimiter() { return lineDelimiter; }
    public void setLineDelimiter(char lineDelimiter) { this.lineDelimiter = lineDelimiter; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DelimitedTXTTreeSchema clone = new DelimitedTXTTreeSchema();

        clone( clone );

        return clone;
    }

    protected void clone( DelimitedTXTTreeSchema treeSchema ) throws CloneNotSupportedException {
        super.clone( treeSchema );
        treeSchema.setLineDelimiter(getLineDelimiter());
        treeSchema.setColumnDelimiter(getColumnDelimiter());
    }

}
