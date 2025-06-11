package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private static final int PORT = 12345;
    private GameLogic logic;

    public void setLogic(GameLogic logic) {
        this.logic = logic;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущено. Очікування на з'єднання...");

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Клієнт підключився: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
//                    System.out.println("Отримано: " + clientMessage);

                    if (logic == null) continue; // если логика еще не установлена, пропускаем

                    if (clientMessage.startsWith("pressed")) {
                        int keyCode = Integer.parseInt(clientMessage.split(" ")[1]);
                        logic.keyPressed(keyCode);
                    } else if (clientMessage.startsWith("released")) {
                        int keyCode = Integer.parseInt(clientMessage.split(" ")[1]);
                        logic.keyReleased(keyCode);
                    }
                }

            } catch (IOException e) {
                System.err.println("Помилка при роботі з клієнтом: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Помилка запуску сервера: " + e.getMessage());
        }
    }
}
