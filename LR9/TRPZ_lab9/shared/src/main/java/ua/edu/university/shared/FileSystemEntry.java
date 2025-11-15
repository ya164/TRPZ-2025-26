package ua.edu.university.shared;

import java.io.Serializable;

public class FileSystemEntry implements Serializable {
    private String name;
    private long size;
    private boolean isDirectory;
    private long lastModified;

    public FileSystemEntry() {}

    public FileSystemEntry(String name, long size, boolean isDirectory, long lastModified) {
        this.name = name;
        this.size = size;
        this.isDirectory = isDirectory;
        this.lastModified = lastModified;
    }

    public String getName() { return name; }
    public long getSize() { return size; }
    public boolean isDirectory() { return isDirectory; }
    public long getLastModified() { return lastModified; }

    @Override
    public String toString() {
        return (isDirectory ? "[DIR] " : "") + name;
    }
}