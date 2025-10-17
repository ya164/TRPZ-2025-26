public class IdleState implements State {
    @Override
    public void viewFiles(FileManager context) {
        System.out.println("[Idle] Переглядаємо файли в поточній директорії");
        context.setState(new ViewingState());
    }

    @Override
    public void copyFile(FileManager context, String fileName) {
        System.out.println("[Idle] Копіюємо файл: " + fileName);
        context.setState(new CopyingState());
    }

    @Override
    public void deleteFile(FileManager context, String fileName) {
        System.out.println("[Idle] Видаляємо файл: " + fileName);
        context.setState(new DeletingState());
    }

    @Override
    public void moveFile(FileManager context, String fileName) {
        System.out.println("[Idle] Переміщуємо файл: " + fileName);
        context.setState(new MovingState());
    }
}