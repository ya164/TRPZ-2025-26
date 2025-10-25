public abstract class FileSystemObject implements Cloneable {

    private String name;
    private String path;

    public FileSystemObject(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String newPath) {
        this.path = newPath;
    }

    @Override
    public FileSystemObject clone() throws CloneNotSupportedException {
        return (FileSystemObject) super.clone();
    }

    public abstract void display(int depth);
}