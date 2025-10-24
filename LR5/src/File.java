public class File extends FileSystemObject {
    private long size;

    public File(String name, String path, long size) {
        super(name, path);
        this.size = size;
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth) + "- " + name + " (File, " + size + " bytes)");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}