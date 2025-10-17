public class FileManager {
    private State currentState;

    public FileManager() {
        this.currentState = new IdleState();
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void viewFiles() {
        currentState.viewFiles(this);
    }

    public void copyFile(String fileName) {
        currentState.copyFile(this, fileName);
    }

    public void deleteFile(String fileName) {
        currentState.deleteFile(this, fileName);
    }

    public void moveFile(String fileName) {
        currentState.moveFile(this, fileName);
    }
}