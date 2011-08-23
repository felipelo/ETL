package br.com.saxes.suite.model;

import br.com.saxes.suite.converter.ValueType;

/**
 *
 * @author felipe
 */
public class NumericTreeNode extends TextTreeNode {

    // fields used for reading purpose
    private String fieldNumFormat;

    public NumericTreeNode() {
        super();
        setValueType(ValueType.NUMERIC);
    }

    public String getFieldNumFormat() { return fieldNumFormat; }
    public void setFieldNumFormat(String fieldNumFormat) { this.fieldNumFormat = fieldNumFormat; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        NumericTreeNode clone = new NumericTreeNode();

        clone( clone );

        return clone;
    }

    protected void clone( NumericTreeNode field ) throws CloneNotSupportedException {
        super.clone( field );
        field.setFieldNumFormat(fieldNumFormat);
    }

}
