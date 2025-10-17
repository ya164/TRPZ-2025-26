public class Main {
    public static void main(String[] args) {
        FileManager commander = new FileManager();

        System.out.println("=== Total Commander Demo ===\n");

        commander.viewFiles();
        commander.copyFile("document.txt");
        commander.deleteFile("temp.tmp");
        commander.moveFile("photo.jpg");
        commander.viewFiles();

        System.out.println("\n=== Повторні операції ===\n");

        commander.copyFile("video.mp4");
        commander.copyFile("music.mp3");
        commander.viewFiles();
    }
}