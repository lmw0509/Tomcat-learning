package ex03.pyrmont.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {
    public String getScheme() {
        return "http";
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            // Accept the next incoming connection from the server socket
            Socket socket;
            try {
                // 等待http请求
                socket = serverSocket.accept();
            } catch (Exception e) {
                continue;
            }

            // Hand this socket off to an HttpProcessor
            // 创建HttpProcessor实例
            HttpProcessor processor = new HttpProcessor(this);

            // 处理请求
            processor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}