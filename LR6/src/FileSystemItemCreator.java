import java.io.File;

public abstract class FileSystemItemCreator {

    public abstract FileSystemItem createFileSystemItem(String path);

    public void performOperation(String path) {
        try {
            FileSystemItem item = createFileSystemItem(path);

            System.out.println("--- Performing operations on: " + item.getName() + " ---");
            item.getInfo();
            String parentDirectory = new File(path).getParent();
            if (parentDirectory == null) {
                parentDirectory = "";
            }
            String newPath = parentDirectory + File.separator + item.getName() + "_moved";
            item.move(newPath);
            item.delete();
            System.out.println("-----------------------------------------\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Operation failed: " + e.getMessage() + "\n");
        }
    }
}