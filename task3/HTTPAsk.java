import tcpclient.TCPClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class HTTPAsk {
    public static void main( String[] args) {

        int port;

        BufferedReader inFromClient;
        DataOutputStream outToClient;

        ServerSocket serverSocket = null;
        port = Integer.parseInt(args[0]);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Failed to listen on port: 8888");
            System.exit(1);
        }

        Socket clientSocket;



        String[] test;
        String[] hostname = new String[20];
        String[] portNumber;
        String[] tempPort = new String[20];
        String toSplit;

        while (true) {
            try {

                clientSocket = serverSocket.accept();
                inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outToClient = new DataOutputStream(clientSocket.getOutputStream());

                if (clientSocket != null) {
                    System.out.println("Connected!");
                }

                toSplit = inFromClient.readLine();
                System.out.println(toSplit);

                test = toSplit.split("hostname=");

                if(test.length > 1) {
                    hostname = test[1].split("&");
                    System.out.println("Hostname: " + hostname[0]);

                    portNumber = test[1].split("port=");
                    tempPort = portNumber[1].split(" ");
                }

                int portNumb = Integer.parseInt(tempPort[0]);


                String fromServer = TCPClient.askServer(hostname[0], portNumb);


                outToClient.writeBytes("HTTP/1.1 200 OK\r\n\r\n" + fromServer);
                //System.out.println(sb.toString());

                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

