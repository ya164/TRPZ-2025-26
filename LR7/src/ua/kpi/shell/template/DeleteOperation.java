package ua.kpi.shell.template;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteOperation extends FileOperation {
    public DeleteOperation(Path source) {
        super(source, null);
    }

    @Override
    protected String getName() {
        return "DeleteOperation";
    }

    @Override
    protected void checkPreconditions() throws Exception {
        if (source == null) throw new IllegalArgumentException("source не може бути null");
        if (!Files.exists(source)) throw new NoSuchFileException("Не існує: " + source);
    }

    @Override
    protected void before() {
        System.out.println("Підготовка до видалення: " + source);
    }

    @Override
    protected void performOperation() throws Exception {
        if (Files.isDirectory(source)) deleteRecursively(source);
        else Files.delete(source);
    }

    @Override
    protected void verify() throws Exception {
        if (Files.exists(source)) throw new IOException("Видалення не вдалося: " + source);
        System.out.println("Перевірка пройшла: об'єкт видалено.");
    }

    private static void deleteRecursively(Path root) throws IOException {
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}