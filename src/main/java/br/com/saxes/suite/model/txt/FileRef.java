package br.com.saxes.suite.model.txt;

public class FileRef {

    private String filePath;

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FileRef clone = new FileRef();

        clone.setFilePath(filePath);

        return clone;
    }

}
