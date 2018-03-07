import tcpclient.TCPClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Handler implements Runnable {
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    private Socket clientSocket;

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Thread started");
        reader();
        return;

    }

    private void reader() {
        try {
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new DataOutputStream(clientSocket.getOutputStream());


            String[] test;
            String[] hostname = new String[20];
            String[] portNumber;
            String[] tempPort = new String[20];
            String toSplit;


                try {

                    inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    outToClient = new DataOutputStream(clientSocket.getOutputStream());

                    if (clientSocket != null) {
                        System.out.println("Connected!");
                    }

                    toSplit = inFromClient.readLine();
                    System.out.println(toSplit);

                    test = toSplit.split("hostname=");

                    if (test.length > 1) {
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


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
