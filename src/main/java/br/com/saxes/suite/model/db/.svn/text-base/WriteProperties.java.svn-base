package br.com.saxes.suite.model.db;

/**
 *
 * @author felipe
 */
public class WriteProperties {

    public static final int INSERT = 1;
    public static final int UPDATE = 2;

    private int action;

    public int getAction() { return action; }
    public void setAction(int action) { this.action = action; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        WriteProperties clone = new WriteProperties();

        clone( clone );

        return clone;
    }

    protected void clone( WriteProperties props ) throws CloneNotSupportedException {
        props.setAction(action);
    }

}
