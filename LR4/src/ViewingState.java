public class ViewingState implements State {
    @Override
    public void viewFiles(FileManager context) {
        System.out.println("[Viewing] Вже переглядаємо файли");
    }

    @Override
    public void copyFile(FileManager context, String fileName) {
        System.out.println("[Viewing] Завершуємо перегляд та копіюємо файл: " + fileName);
        context.setState(new CopyingState());
    }

    @Override
    public void deleteFile(FileManager context, String fileName) {
        System.out.println("[Viewing] Завершуємо перегляд та видаляємо файл: " + fileName);
        context.setState(new DeletingState());
    }

    @Override
    public void moveFile(FileManager context, String fileName) {
        System.out.println("[Viewing] Завершуємо перегляд та переміщуємо файл: " + fileName);
        context.setState(new MovingState());
    }
}