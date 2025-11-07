package ua.kpi.shell.template;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyOperation extends FileOperation {
    public CopyOperation(Path source, Path target) {
        super(source, target);
    }

    @Override
    protected String getName() {
        return "CopyOperation";
    }

    @Override
    protected void checkPreconditions() throws Exception {
        if (source == null || target == null) throw new IllegalArgumentException("source/target не можуть бути null");
        if (!Files.exists(source)) throw new NoSuchFileException("Не існує: " + source);
        if (Files.exists(target)) throw new FileAlreadyExistsException("Ціль вже існує: " + target);
    }

    @Override
    protected void before() {
        System.out.println("Підготовка до копіювання: " + source + " -> " + target);
    }

    @Override
    protected void performOperation() throws Exception {
        if (Files.isDirectory(source)) {
            copyDirectory(source, target);
        } else {
            Files.createDirectories(target.getParent());
            Files.copy(source, target);
        }
    }

    @Override
    protected void verify() throws Exception {
        if (!Files.exists(target)) throw new IOException("Копіювання не вдалося: " + target);
        System.out.println("Перевірка пройшла: ціль існує -> " + target);
    }

    private static void copyDirectory(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path rel = from.relativize(dir);
                Files.createDirectories(to.resolve(rel));
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path rel = from.relativize(file);
                Files.copy(file, to.resolve(rel));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}

