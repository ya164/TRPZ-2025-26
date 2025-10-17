public class CopyingState implements State {
    @Override
    public void viewFiles(FileManager context) {
        System.out.println("[Copying] Копіювання завершено. Переглядаємо файли");
        context.setState(new ViewingState());
    }

    @Override
    public void copyFile(FileManager context, String fileName) {
        System.out.println("[Copying] Копіюємо ще один файл: " + fileName);
    }

    @Override
    public void deleteFile(FileManager context, String fileName) {
        System.out.println("[Copying] Копіювання завершено. Видаляємо файл: " + fileName);
        context.setState(new DeletingState());
    }

    @Override
    public void moveFile(FileManager context, String fileName) {
        System.out.println("[Copying] Копіювання завершено. Переміщуємо файл: " + fileName);
        context.setState(new MovingState());
    }
}