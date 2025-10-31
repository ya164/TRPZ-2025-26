import java.io.File;

public class FileItem extends FileSystemItem {
    private long size;

    public FileItem(String path) {
        super(path);
        File file = new File(path);

        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Path does not point to a valid file: " + path);
        }

        this.size = file.length();
    }

    @Override
    public void getInfo() {
        System.out.println("File: " + name + ", Size: " + size + " bytes, Path: " + path);
    }

    @Override
    public void delete() {
        System.out.println("-> Deleting file: " + name);
    }

    @Override
    public void move(String newPath) {
        System.out.println("-> Moving file from '" + path + "' to '" + newPath + "'");
    }
}