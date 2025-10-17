public class MovingState implements State {
    @Override
    public void viewFiles(FileManager context) {
        System.out.println("[Moving] Переміщення завершено. Переглядаємо файли");
        context.setState(new ViewingState());
    }

    @Override
    public void copyFile(FileManager context, String fileName) {
        System.out.println("[Moving] Переміщення завершено. Копіюємо файл: " + fileName);
        context.setState(new CopyingState());
    }

    @Override
    public void deleteFile(FileManager context, String fileName) {
        System.out.println("[Moving] Переміщення завершено. Видаляємо файл: " + fileName);
        context.setState(new DeletingState());
    }

    @Override
    public void moveFile(FileManager context, String fileName) {
        System.out.println("[Moving] Переміщуємо ще один файл: " + fileName);
    }
}