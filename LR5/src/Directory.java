import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Directory extends FileSystemObject {

    private final List<FileSystemObject> children;

    public Directory(String name, String path) {
        super(name, path);
        this.children = Collections.emptyList();
    }

    private Directory(String name, String path, List<FileSystemObject> children) {
        super(name, path);
        this.children = Collections.unmodifiableList(children);
    }

    public Directory add(FileSystemObject child) {
        List<FileSystemObject> newChildren = new ArrayList<>(this.children);
        newChildren.add(child);
        return new Directory(this.getName(), this.getPath(), newChildren);
    }

    public List<FileSystemObject> getChildren() {
        return children;
    }

    @Override
    public Directory clone() throws CloneNotSupportedException {
        Directory cloned = (Directory) super.clone();

        List<FileSystemObject> clonedChildren = new ArrayList<>();
        for (FileSystemObject child : this.children) {
            clonedChildren.add(child.clone());
        }

        return new Directory(cloned.getName(), cloned.getPath(), clonedChildren);
    }

    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth);
        System.out.println(indent + "[DIR] " + getName());
        for (FileSystemObject child : children) {
            child.display(depth + 2);
        }
    }
}