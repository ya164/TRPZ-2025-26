public interface State {
    void viewFiles(FileManager context);
    void copyFile(FileManager context, String fileName);
    void deleteFile(FileManager context, String fileName);
    void moveFile(FileManager context, String fileName);
}