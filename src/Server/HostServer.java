package Server;

import com.sun.security.ntlm.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HostServer {

    private static ArrayList<ClientThread> clients;
    private static int oldPort;
    private static ServerSocket oldServerSocket;

    private ClientThread playerOne;
    private ClientThread playerTwo;
    private ServerSocket serverSocket;

    public HostServer(){
        try{
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(0);
            System.out.println("Awaiting connection on port " + serverSocket.getLocalPort());
        } catch(Exception e) {e.printStackTrace();}
    }

    public static void main(String args[]){
        clients = new ArrayList<>();
        oldPort = 8080;
        try{
            oldServerSocket = new ServerSocket(oldPort);
            while(true){
                System.out.println("Waiting for connections on oldPort " + oldPort + ".");
                Socket socket = oldServerSocket.accept();

                ClientThread client = new ClientThread(socket);
                clients.add(client);
                client.start();
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    private static synchronized void broadcast(String message){
        for(ClientThread client : clients){
            client.sendMessage(message);
        }
    }

    static class ClientThread extends Thread {

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

        public ClientThread(Socket socket){
            this.socket = socket;
            try{
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {e.printStackTrace();}

            try{
                String user = (String) sInput.readObject();
                System.out.println(user + " has connected");
            } catch (Exception e) { e.printStackTrace();}
        }

        public void sendMessage(String message){
            try{
                sOutput.writeObject(message);
            } catch (Exception e) {e.printStackTrace();}
        }

        @Override
        public void run() {
            while(true){
                try{
                    String message = (String) sInput.readObject();
                    broadcast(message);
                } catch (Exception e) {e.printStackTrace();}
            }
        }
    }
}

