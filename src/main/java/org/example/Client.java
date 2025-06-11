package org.example;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final int PORT = 12345;

    private PrintWriter out;

    public Client() {
        try {

            Socket socket = new Socket("localhost", PORT);

            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendKey(String message) {
        if (out != null) {
            out.println(message);
        }
    }
}
