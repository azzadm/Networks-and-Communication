package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {

        String fromServer;
        Socket clientSocket = new Socket(hostname, port);
        StringBuilder sb = new StringBuilder();
        clientSocket.setSoTimeout(2000);

        try{
            DataOutputStream outputToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputToServer.writeBytes(ToServer + '\n');

            while ((fromServer = inputFromServer.readLine()) != null) {
                sb.append(fromServer);
                if(sb.length()>4000){
                    break;
                }
            }
        } catch (SocketTimeoutException s){
        }

        clientSocket.close();

        return sb.toString();
    }

    public static String askServer(String hostname, int port) throws IOException {
        return askServer(hostname, port, "");
    }
}