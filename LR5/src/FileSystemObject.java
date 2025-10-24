public abstract class FileSystemObject implements Cloneable {
    protected String name;
    protected String path;

    public FileSystemObject(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public abstract void display(int depth);

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}