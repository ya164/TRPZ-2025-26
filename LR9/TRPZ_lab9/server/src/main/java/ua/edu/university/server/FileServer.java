package ua.edu.university.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer {

    private static final int PORT = 8888;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("SERVER STARTED");
            while (true) {
                Socket client = serverSocket.accept();
                pool.execute(new ClientHandler(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
