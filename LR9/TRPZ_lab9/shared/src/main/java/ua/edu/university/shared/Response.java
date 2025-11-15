package ua.edu.university.shared;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private boolean success;
    private String errorMessage;
    private List<String> drives;
    private List<FileSystemEntry> entries;

    public boolean isSuccess() { return success; }
    public String getErrorMessage() { return errorMessage; }
    public List<String> getDrives() { return drives; }
    public List<FileSystemEntry> getEntries() { return entries; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setDrives(List<String> drives) { this.drives = drives; }
    public void setEntries(List<FileSystemEntry> entries) { this.entries = entries; }
}
