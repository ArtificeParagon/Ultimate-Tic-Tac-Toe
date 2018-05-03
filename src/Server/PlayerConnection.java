package Server;

import Game.Renderer;

import java.io.*;
import java.net.Socket;

//X and O will each have a player connection created
public class PlayerConnection implements Runnable{

    private Socket connection;
    private ObjectInputStream inputFromServer;
    private ObjectOutputStream outputToServer;
    private boolean running = true;

    public PlayerConnection(String ip, int port) {
        connectToServer(ip, port);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running){
            receivePlay();
        }
    }

    private void connectToServer(String ip, int port){
        try{
            connection = new Socket(ip, port);
            outputToServer = new ObjectOutputStream(connection.getOutputStream());
            inputFromServer = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {e.printStackTrace();}
    }

    public void sendPlay(int boardX, int boardY, int pieceX, int pieceY){
        String message = boardX + "-" + boardY + "-" + pieceX + "-" + pieceY;
        try {
            outputToServer.writeObject(message);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void receivePlay(){
        int[] play = new int[4];
        char player = ' ';
        System.out.println("Ready to receive");
        try {
            String turn = (String) inputFromServer.readObject();
            System.out.println("received");
            String[] coords = turn.split("-");
            for(int i = 0; i < play.length; i++){
                play[i] = Integer.parseInt(coords[i]);
            }
            player = coords[4].charAt(0);
        } catch (Exception e) {e.printStackTrace();}

        switch (player){
            case 'x':
                Renderer.getInstance().drawX(play[0], play[1], play[2], play[3]);
                break;
            case 'o':
                Renderer.getInstance().drawO(play[0], play[1], play[2], play[3]);
                break;
        }
    }

}
