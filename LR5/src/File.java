public class File extends FileSystemObject {

    private int sizeKb;

    public File(String name, String path, int sizeKb) {
        super(name, path);
        this.sizeKb = sizeKb;
    }

    public int getSizeKb() {
        return sizeKb;
    }

    public void setSizeKb(int sizeKb) {
        this.sizeKb = sizeKb;
    }

    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth);
        System.out.println(indent + getName() + " (" + sizeKb + " KB)");
    }

}