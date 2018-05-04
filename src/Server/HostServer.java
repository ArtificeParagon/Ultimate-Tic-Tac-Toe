package Server;

import Game.Game;
import com.sun.security.ntlm.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HostServer implements Runnable {

    private ClientThread playerOne;
    private ClientThread playerTwo;
    private ClientThread currentPlayer;
    private ServerSocket serverSocket;
    private boolean running = true;

    private Game game;

    public HostServer(){
        try{
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(0);
            System.out.println("Awaiting connection on IP: " + serverSocket.getInetAddress().getHostName() + ", port: " + serverSocket.getLocalPort());
        } catch(Exception e) {e.printStackTrace();}
    }

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public void shutdown(){
        running = false;
        broadcastAction('q');
    }

    private void broadcastAction(char action){
        playerOne.sendAction(action);
        if(playerTwo != null)
            playerTwo.sendAction(action);
    }

    private void broadcast(String message){
        playerOne.sendMessage(message);
        playerTwo.sendMessage(message);
    }
    private void broadcastPlay(int boardX, int boardY, int pieceX, int pieceY, char player, int next){
        String message = boardX + "-" + boardY + "-" + pieceX + "-" + pieceY + "-" + player + '-' + next;
        broadcast(message);
    }
    private void broadcastPlay(int[] play, char player){
        broadcastAction('p');
        broadcastPlay(play[0], play[1], play[2], play[3], player, game.getNextBoard());

    }
    private void broadcastTextMessage(String message){
        playerOne.sendTextMessage(message);
        playerTwo.sendTextMessage(message);
    }

    @Override
    public void run() {
        try {
            Socket playerOneSocket = serverSocket.accept();
            playerOne = new ClientThread(playerOneSocket, 'x');
            System.out.println("X connected");
            playerOne.sendTextMessage("Server started on port: " + getPort());

            Socket playerTwoSocket = serverSocket.accept();
            playerTwo = new ClientThread(playerTwoSocket, 'o');
            System.out.println("O Connected");

            playerOne.sendTextMessage("You are X");
            playerTwo.sendTextMessage("You are O");

            boolean validTurn = false;
            int[] turn;
            currentPlayer = playerOne;

            game = new Game();
            while(running){
                do {
                    turn = currentPlayer.getTurn();
                    validTurn = game.isMoveValid(turn[0], turn[1], turn[2], turn[3]);
                    if(!validTurn) invalidTurn();
                } while (!validTurn);
                game.playPiece(turn, currentPlayer.getPlayer());
                broadcastPlay(turn, currentPlayer.getPlayer());
                if(game.isWon()){
                    broadcastTextMessage(currentPlayer.getPlayer() + " HAS WON");
                    running = false;
                }

                // Switch current player
                if(currentPlayer == playerOne) currentPlayer = playerTwo;
                else currentPlayer = playerOne;
            }

            shutdown();
        } catch(Exception e){e.printStackTrace();}

    }

    private void invalidTurn(){
        currentPlayer.sendTextMessage("Invalid Move");
    }

    //TODO: Edit this to better suite the game
    static class ClientThread {

        Socket socket;
        char player;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

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

        public ClientThread(Socket socket, char player){
            this.player = player;
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

        public void sendTextMessage(String message){
            try{
                sOutput.writeChar('i');
                sOutput.writeObject(message);
            } catch (Exception e) {e.printStackTrace();}
        }

        public void sendAction(char action){
            try{
                sOutput.writeChar(action);
            } catch (Exception e) {e.printStackTrace();}
        }

        public char getPlayer(){
            return player;
        }
    }
}

