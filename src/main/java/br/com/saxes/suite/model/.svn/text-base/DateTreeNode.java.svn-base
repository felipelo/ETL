package br.com.saxes.suite.model;

import br.com.saxes.suite.converter.ValueType;

/**
 *
 * @author felipe
 */
public class DateTreeNode extends TextTreeNode {

    // fields used for reading purpose
    private String fieldDateFormat;

    public DateTreeNode() {
        super();
        setValueType(ValueType.DATE);
    }

    public String getFieldDateFormat() { return fieldDateFormat; }
    public void setFieldDateFormat(String fieldDateFormat) { this.fieldDateFormat = fieldDateFormat; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DateTreeNode clone = new DateTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( DateTreeNode field ) throws CloneNotSupportedException {
        super.clone( field );
        field.setFieldDateFormat(fieldDateFormat);
    }

}
