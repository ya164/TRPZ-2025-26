import java.io.File;

public class DirectoryItem extends FileSystemItem {

    public DirectoryItem(String path) {
        super(path);
        File file = new File(path);

        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("Path does not point to a valid directory: " + path);
        }
    }

    @Override
    public void getInfo() {
        System.out.println("Directory: " + name + ", Path: " + path);
    }

    @Override
    public void delete() {
        System.out.println("-> Deleting directory: " + name);
    }

    @Override
    public void move(String newPath) {
        System.out.println("-> Moving directory from '" + path + "' to '" + newPath + "'");
    }
}