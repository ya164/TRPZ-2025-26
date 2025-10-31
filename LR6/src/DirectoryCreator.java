public class DirectoryCreator extends FileSystemItemCreator {

    @Override
    public FileSystemItem createFileSystemItem(String path) {
        return new DirectoryItem(path);
    }
}