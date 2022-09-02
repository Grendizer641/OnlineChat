package ru.korneev.server;

import ru.korneev.server.chat.Server;

public class ServerStart {

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if(args.length != 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server().start(port);
    }
}
