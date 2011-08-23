package br.com.saxes.suite.model.db;

public class ConnectionWrapper {

    private String name;
    private String driver;
    private String connURL;
    private String user;
    private String password;

    public String getConnURL() { return connURL; }
    public void setConnURL(String connURL) { this.connURL = connURL; }

    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ConnectionWrapper conn = new ConnectionWrapper();

        clone( conn );

        return conn;
    }

    protected void clone( ConnectionWrapper conn ) throws CloneNotSupportedException {
        conn.setConnURL(connURL);
        conn.setDriver(driver);
        conn.setName(name);
        conn.setPassword(password);
        conn.setUser(user);
    }
}
