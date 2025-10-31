public class FileCreator extends FileSystemItemCreator {

    @Override
    public FileSystemItem createFileSystemItem(String path) {
        return new FileItem(path);
    }
}