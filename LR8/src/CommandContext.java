import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class CommandContext {
    private Path currentDirectory;

    public CommandContext(Path startDirectory) {
        this.currentDirectory = startDirectory.toAbsolutePath().normalize();
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(Path currentDirectory) {
        this.currentDirectory = currentDirectory.toAbsolutePath().normalize();
    }

    public Path resolvePath(String arg) {
        Path p = Paths.get(arg);
        if (!p.isAbsolute()) {
            p = currentDirectory.resolve(p);
        }
        return p.normalize();
    }

    public void listDirectory() {
        try (Stream<Path> stream = Files.list(currentDirectory)) {
            stream.forEach(p -> System.out.println(p.getFileName()));
        } catch (IOException e) {
            System.out.println("Помилка під час читання директорії: " + e.getMessage());
        }
    }

    public void changeDirectory(String pathArg) {
        Path target = resolvePath(pathArg);
        if (Files.isDirectory(target)) {
            setCurrentDirectory(target);
            System.out.println("Поточна директорія: " + currentDirectory);
        } else {
            System.out.println("Директорію не знайдено: " + target);
        }
    }

    public void copy(String srcArg, String dstArg) {
        Path src = resolvePath(srcArg);
        Path dst = resolvePath(dstArg);

        try {
            if (!Files.exists(src)) {
                System.out.println("Помилка: джерело не існує: " + src);
                return;
            }

            if (Files.isDirectory(src)) {
                copyDirectory(src, dst);
            } else {
                if (Files.exists(dst) && Files.isDirectory(dst)) {
                    dst = dst.resolve(src.getFileName());
                }
                Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Скопійовано: " + src + " -> " + dst);

        } catch (IOException e) {
            System.out.println("Помилка копіювання: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Помилка під час рекурсивного копіювання: " + e.getMessage());
        }
    }

    private void copyDirectory(Path src, Path dst) throws IOException {
        if (Files.notExists(dst)) {
            Files.createDirectories(dst);
        }

        try (Stream<Path> stream = Files.walk(src)) {
            stream.forEach(sourcePath -> {
                try {
                    Path relative = src.relativize(sourcePath);
                    Path target = dst.resolve(relative);

                    if (Files.isDirectory(sourcePath)) {
                        if (Files.notExists(target)) {
                            Files.createDirectories(target);
                        }
                    } else {
                        Files.copy(sourcePath, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void move(String srcArg, String dstArg) {
        Path src = resolvePath(srcArg);
        Path dst = resolvePath(dstArg);

        try {
            if (!Files.exists(src)) {
                System.out.println("Помилка: джерело не існує: " + src);
                return;
            }

            if (Files.exists(dst) && Files.isDirectory(dst)) {
                dst = dst.resolve(src.getFileName());
            }

            Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Переміщено: " + src + " -> " + dst);

        } catch (IOException e) {
            System.out.println("Помилка переміщення: " + e.getMessage());
        }
    }

    public void delete(String pathArg) {
        Path target = resolvePath(pathArg);
        try {
            Files.delete(target);
            System.out.println("Видалено: " + target);
        } catch (IOException e) {
            System.out.println("Помилка видалення: " + e.getMessage());
        }
    }

    public void createDirectory(String pathArg) {
        Path dir = resolvePath(pathArg);
        try {
            if (Files.exists(dir)) {
                System.out.println("Помилка: об'єкт із такою назвою вже існує: " + dir);
                return;
            }
            Files.createDirectory(dir);
            System.out.println("Створено директорію: " + dir);
        } catch (IOException e) {
            System.out.println("Помилка створення директорії: " + e.getMessage());
        }
    }
}
