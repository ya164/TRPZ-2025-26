import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemObject {
    private List<FileSystemObject> children = new ArrayList<>();

    public Directory(String name, String path) {
        super(name, path);
    }

    public void add(FileSystemObject child) {
        children.add(child);
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth) + "[" + name + "] (Directory)");
        for (FileSystemObject child : children) {
            child.display(depth + 2);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Directory clone = (Directory) super.clone();

        clone.children = new ArrayList<>();

        for (FileSystemObject child : this.children) {
            clone.add((FileSystemObject) child.clone());
        }

        return clone;
    }
}