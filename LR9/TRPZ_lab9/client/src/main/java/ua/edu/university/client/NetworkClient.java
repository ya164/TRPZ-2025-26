package ua.edu.university.client;

import com.google.gson.Gson;
import ua.edu.university.shared.*;

import java.io.*;
import java.net.Socket;

public class NetworkClient {
    private final String ip;
    private final int port;
    private final Gson gson = new Gson();

    public NetworkClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Response send(Request req) {
        try (
                Socket socket = new Socket(ip, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(gson.toJson(req));
            return gson.fromJson(in.readLine(), Response.class);

        } catch (Exception e) {
            Response r = new Response();
            r.setSuccess(false);
            r.setErrorMessage(e.getMessage());
            return r;
        }
    }
}
