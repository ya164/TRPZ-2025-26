package ua.kpi.shell.template;

import java.io.IOException;
import java.nio.file.*;

public class MoveOperation extends FileOperation {
    public MoveOperation(Path source, Path target) {
        super(source, target);
    }

    @Override
    protected String getName() {
        return "MoveOperation";
    }

    @Override
    protected void checkPreconditions() throws Exception {
        if (source == null || target == null) throw new IllegalArgumentException("source/target не можуть бути null");
        if (!Files.exists(source)) throw new NoSuchFileException("Не існує: " + source);
        if (Files.exists(target)) throw new FileAlreadyExistsException("Ціль вже існує: " + target);
    }

    @Override
    protected void before() {
        System.out.println("Підготовка до переміщення: " + source + " -> " + target);
    }

    @Override
    protected void performOperation() throws Exception {
        try {
            Files.createDirectories(target.getParent());
            Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("ATOMIC_MOVE недоступний, виконуємо copy+delete...");
            new CopyOperation(source, target).execute();
            new DeleteOperation(source).execute();
        }
    }

    @Override
    protected void verify() throws Exception {
        if (!Files.exists(target)) throw new IOException("Переміщення не вдалося: " + target);
        if (Files.exists(source)) throw new IOException("Переміщення не завершено: " + source);
        System.out.println("Перевірка пройшла: ціль існує, джерела нема.");
    }
}