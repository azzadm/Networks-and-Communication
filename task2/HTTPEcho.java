import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main(String[] args) {

        int port;
        Socket clientSocket;
        ServerSocket serverSocket = null;
        port = Integer.parseInt(args[0]);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Failed to listen on port: 8888");
            System.exit(1);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                StringBuilder sb = new StringBuilder();


                if (clientSocket != null) {
                    System.out.println("Connected!");
                }

                String temp = ".";

                while (!temp.equals("")) {
                    temp = inFromClient.readLine();
                    sb.append(temp + "\r\n");
                }

                String end = "HTTP/1.1 200 OK\r\n\r\n" + sb.toString();

                outToClient.writeBytes(end);
                //System.out.println(sb.toString());

                clientSocket.close();

            } catch (IOException e) {
                System.err.println("Error.");
                System.exit(1);
            }
        }
    }
}