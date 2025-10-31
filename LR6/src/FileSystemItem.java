import java.io.File;

public abstract class FileSystemItem {
    protected String name;
    protected String path;

    public FileSystemItem(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty.");
        }
        this.path = path;
        this.name = new File(path).getName();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public abstract void getInfo();

    public abstract void delete();

    public abstract void move(String newPath);
}