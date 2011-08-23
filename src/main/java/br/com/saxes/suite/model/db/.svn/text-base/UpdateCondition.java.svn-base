package br.com.saxes.suite.model.db;

import br.com.saxes.suite.model.TextTreeNode;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

/**
 *
 * @author felipe
 */
@XmlAccessorType(value=XmlAccessType.FIELD)
public class UpdateCondition {

    public static final String EQUAL = "=";
    public static final String NOT_EQUAL = "<>";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String GREATER_THAN_EQUAL = ">=";
    public static final String LESS_THAN_EQUAL = "<=";
    public static final String BETWEEN = "BETWEEN";
    public static final String LIKE = "LIKE";
    public static final String IN = "IN";

    @XmlIDREF
    private TextTreeNode column;
    private String operator;

    public TextTreeNode getColumn() { return column; }
    public void setColumn(TextTreeNode column) { this.column = column; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        UpdateCondition _where = new UpdateCondition();

        clone( _where );

        return _where;
    }

    protected void clone( UpdateCondition where ) throws CloneNotSupportedException {
        where.setColumn((TextTreeNode) column.clone());
        where.setOperator(operator);
    }
}
