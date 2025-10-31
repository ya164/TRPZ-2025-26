public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Shell Application simulation...\n");

        String filePath = "C:/temp/my_document.txt";
        String dirPath = "C:/temp/my_folder";
        String nonExistentPath = "C:/temp/non_existent_file.txt";

        FileSystemItemCreator fileCreator = new FileCreator();
        FileSystemItemCreator directoryCreator = new DirectoryCreator();

        System.out.println("1. Processing a valid file...");
        fileCreator.performOperation(filePath);

        System.out.println("2. Processing a valid directory...");
        directoryCreator.performOperation(dirPath);

        System.out.println("3. Processing a non-existent file...");
        fileCreator.performOperation(nonExistentPath);

        System.out.println("\nShell Application simulation finished.");
    }
}