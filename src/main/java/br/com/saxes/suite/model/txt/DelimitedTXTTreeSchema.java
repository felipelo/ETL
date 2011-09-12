package br.com.saxes.suite.model.txt;

/**
 *
 * @author felipe
 */
public class DelimitedTXTTreeSchema extends TXTTreeSchema {

    private String lineDelimiter;
    private String columnDelimiter;

    public DelimitedTXTTreeSchema() {
        super( FileModelStrategy.DELIMITED );
		lineDelimiter = "";
		columnDelimiter = "";
    }

    public String getColumnDelimiter() { return columnDelimiter; }
    public void setColumnDelimiter(String columnDelimiter) { this.columnDelimiter = columnDelimiter; }

    public String getLineDelimiter() { return lineDelimiter; }
    public void setLineDelimiter(String lineDelimiter) { this.lineDelimiter = lineDelimiter; }

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
