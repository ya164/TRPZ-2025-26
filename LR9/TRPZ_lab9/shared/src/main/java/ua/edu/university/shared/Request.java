package ua.edu.university.shared;

import java.io.Serializable;

public class Request implements Serializable {
    private CommandType command;
    private String path1;
    private String path2;

    public Request() {}

    public Request(CommandType command, String path1, String path2) {
        this.command = command;
        this.path1 = path1;
        this.path2 = path2;
    }

    public CommandType getCommand() { return command; }
    public String getPath1() { return path1; }
    public String getPath2() { return path2; }
}
