public class DeletingState implements State {
    @Override
    public void viewFiles(FileManager context) {
        System.out.println("[Deleting] Видалення завершено. Переглядаємо файли");
        context.setState(new ViewingState());
    }

    @Override
    public void copyFile(FileManager context, String fileName) {
        System.out.println("[Deleting] Видалення завершено. Копіюємо файл: " + fileName);
        context.setState(new CopyingState());
    }

    @Override
    public void deleteFile(FileManager context, String fileName) {
        System.out.println("[Deleting] Видаляємо ще один файл: " + fileName);
    }

    @Override
    public void moveFile(FileManager context, String fileName) {
        System.out.println("[Deleting] Видалення завершено. Переміщуємо файл: " + fileName);
        context.setState(new MovingState());
    }
}