package ua.kpi.shell.template;

import java.io.IOException;
import java.nio.file.*;

public class Demo {
    public static void main(String[] args) {
        System.out.println("DEMO Template Method: Shell Operations\n");

        Path base = Paths.get(System.getProperty("user.home"), "TemplateMethodDemo");
        Path sourceDir = base.resolve("sourceDir");
        Path targetDir = base.resolve("targetDir");
        Path file = sourceDir.resolve("demo.txt");
        Path copyInTarget = targetDir.resolve("demo_copy.txt");
        Path movedIntoTarget = targetDir.resolve("demo_moved.txt");

        try {
            prepareEnvironment(base, sourceDir, targetDir, file);

            if (Files.exists(copyInTarget)) Files.delete(copyInTarget);
            new CopyOperation(file, copyInTarget).execute();

            if (Files.exists(movedIntoTarget)) Files.delete(movedIntoTarget);
            new MoveOperation(file, movedIntoTarget).execute();

            new DeleteOperation(movedIntoTarget).execute();

            System.out.println("\nСтруктура після операцій:");
            System.out.println(" - " + sourceDir);
            System.out.println(" - " + targetDir);

            System.out.println("\nDEMO завершено");

        } catch (Exception e) {
            System.err.println("DEMO ERROR: " + e.getMessage());
        }
    }

    private static void prepareEnvironment(Path base, Path sourceDir, Path targetDir, Path file) throws IOException {
        System.out.println("Створення робочих папок: " + base);
        Files.createDirectories(sourceDir);
        Files.createDirectories(targetDir);
        if (!Files.exists(file)) {
            Files.writeString(file, "Це тестовий файл для демонстрації Template Method.\n");
            System.out.println("Створено файл: " + file);
        } else {
            System.out.println("Файл вже існує: " + file);
        }
        System.out.println("Початкове середовище підготовлено.\n");
    }
}