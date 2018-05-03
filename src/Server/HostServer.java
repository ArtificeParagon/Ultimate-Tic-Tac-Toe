package Server;

import com.sun.security.ntlm.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HostServer implements Runnable {

    private static ArrayList<ClientThread> clients;
    private static int oldPort;
    private static ServerSocket oldServerSocket;

    private ClientThread playerOne;
    private ClientThread playerTwo;
    private ServerSocket serverSocket;
    private boolean running = true;

    public HostServer(){
        try{
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(8080);
            System.out.println("Awaiting connection on port " + serverSocket.getLocalPort());
        } catch(Exception e) {e.printStackTrace();}
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public void shutdown(){
            playerOne.stopRunning();
            playerTwo.stopRunning();
            this.running = false;
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

    private void broadcast(String message){
        playerOne.sendMessage(message);
        playerTwo.sendMessage(message);
    }
    private void broadcastPlay(int boardX, int boardY, int pieceX, int pieceY, char player){
        String message = boardX + "-" + boardY + "-" + pieceX + "-" + pieceY + "-" + player;
        broadcast(message);
    }
    private void broadcastPlay(int[] play, char player){
        broadcastPlay(play[0], play[1], play[2], play[3], player);
    }

    @Override
    public void run() {
        try {
            Socket playerOneSocket = serverSocket.accept();
            playerOne = new ClientThread(playerOneSocket);
            System.out.println("X connected");

            Socket playerTwoSocket = serverSocket.accept();
            playerTwo = new ClientThread(playerTwoSocket);
            System.out.println("O Connected");

            playerOne.start();
            playerTwo.start();

            while(running){
                broadcastPlay(playerOne.getTurn(), 'x');
                broadcastPlay(playerTwo.getTurn(), 'o');
            }
        } catch(Exception e){e.printStackTrace();}

    }

    //TODO: Edit this to better suite the game
    static class ClientThread extends Thread {

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

        boolean running = true;
        public void stopRunning(){
            running = false;
        }

        public int[] getTurn(){
            int[] play = new int[4];
            try {
                String turn = (String) sInput.readObject();
                String[] coords = turn.split("-");
                for(int i = 0; i < play.length; i++){
                    play[i] = Integer.parseInt(coords[i]);
                }
                System.out.println(turn);
            } catch (Exception e) {e.printStackTrace();}
            return play;
        }

        public ClientThread(Socket socket){
            this.socket = socket;
            try{
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {e.printStackTrace();}
        }

        public void sendMessage(String message){
            try{
                sOutput.writeObject(message);
            } catch (Exception e) {e.printStackTrace();}
        }

        @Override
        public void run() {
            //TODO: Add run code
        }
    }
}

