package br.com.saxes.suite.model.txt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author felipe
 */
@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
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
