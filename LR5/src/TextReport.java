public class TextReport extends FileSystemObject {

    private String author;
    private String body;

    public TextReport(String name, String path, String author, String body) {
        super(name, path);
        this.author = author;
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String b) {
        this.body = b;
    }

    @Override
    public TextReport clone() throws CloneNotSupportedException {
        return (TextReport) super.clone();
    }

    @Override
    public void display(int depth) {
        String indent = " ".repeat(depth);
        System.out.println(indent + getName() + " [TEXT REPORT] by " + author);
    }
}
