package ua.edu.university.server;

import com.google.gson.Gson;
import ua.edu.university.shared.CommandType;
import ua.edu.university.shared.FileSystemEntry;
import ua.edu.university.shared.Request;
import ua.edu.university.shared.Response;

import java.io.*;
import java.net.Socket;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Gson gson = new Gson();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String json = in.readLine();
            if (json == null) {
                return;
            }

            Request req = gson.fromJson(json, Request.class);
            Response res = handle(req);
            out.println(gson.toJson(res));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private Response handle(Request req) {
        Response res = new Response();

        try {
            switch (req.getCommand()) {

                case GET_DRIVES: {
                    List<String> drives = Arrays.stream(File.listRoots())
                            .map(File::getAbsolutePath)
                            .collect(Collectors.toList());
                    res.setDrives(drives);
                    break;
                }

                case GET_DIRECTORY_CONTENT: {
                    Path path = Paths.get(req.getPath1());
                    List<FileSystemEntry> entries = new ArrayList<>();

                    if (path.getParent() != null) {
                        entries.add(new FileSystemEntry("..", 0, true, 0));
                    }

                    try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                        for (Path p : dir) {
                            File f = p.toFile();
                            entries.add(new FileSystemEntry(
                                    f.getName(),
                                    f.length(),
                                    f.isDirectory(),
                                    f.lastModified()
                            ));
                        }
                    }
                    res.setEntries(entries);
                    break;
                }

                case CREATE_DIRECTORY: {
                    Path dirPath = Paths.get(req.getPath1());
                    Files.createDirectories(dirPath);
                    break;
                }

                case COPY: {
                    Path source = Paths.get(req.getPath1());
                    Path target = Paths.get(req.getPath2());
                    copyRecursively(source, target);
                    break;
                }

                case DELETE: {
                    Path toDelete = Paths.get(req.getPath1());
                    deleteRecursively(toDelete);
                    break;
                }

                default:
                    throw new UnsupportedOperationException("Unknown command: " + req.getCommand());
            }

            res.setSuccess(true);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setErrorMessage(e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        return res;
    }

    private void copyRecursively(Path source, Path target) throws IOException {
        if (!Files.exists(source)) {
            throw new FileNotFoundException("Source not found: " + source);
        }

        if (Files.isDirectory(source)) {
            Files.createDirectories(target);

            try (Stream<Path> stream = Files.walk(source)) {
                stream.forEach(p -> {
                    Path relative = source.relativize(p);
                    Path dest = target.resolve(relative);
                    try {
                        if (Files.isDirectory(p)) {
                            Files.createDirectories(dest);
                        } else {
                            Files.copy(p, dest, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                });
            } catch (UncheckedIOException ex) {
                throw ex.getCause();
            }

        } else {
            Files.createDirectories(target.getParent());
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (!Files.exists(path)) {
            return;
        }

        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
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
