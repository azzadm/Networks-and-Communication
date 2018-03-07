import tcpclient.TCPClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcHTTPAsk extends Thread {
    private static ConcHTTPAsk server;
    private ServerSocket serverSocket;
    Socket clientSocket = null;

    BufferedReader inFromClient = null;
    DataOutputStream outToClient = null;
    int port;

    public ConcHTTPAsk(int port) {
        this.port = port;
    }


    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Started to listen for connections at port: " + port);
        } catch (IOException e) {
            System.err.println("Failed to listen on port: 8888");
            System.exit(1);
        }


        while (true) {
            System.out.println("Waiting for clients...");
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Connected!");

                Thread thread = new Thread(new Handler(clientSocket));
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }




/*
        String[] test;
        String[] hostname = new String[20];
        String[] portNumber;
        String[] tempPort = new String[20];
        String toSplit;

        while (true) {
            try {

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
        }*/
    }


    public static void main(String[] args) {


        server = new ConcHTTPAsk(8888);
        server.start();

    }
}
