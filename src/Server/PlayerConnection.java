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
        thread.setName("PC THREAD");
        thread.start();
    }

    @Override
    public void run() {
        while (running){
            char action = receiveAction();
            switch(action){
                case 'p':
                    receivePlay();
                    break;
                case 'i':
                    receiveMessage();
                    break;
                case 'q':
                    quitReceieved();
                    break;
                case 'n':
                    colorNextSquare();
                    break;
                case 'w':
                    receiveMessage();
                    gameWonAlert();
                    break;
            }
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

    private char receiveAction(){
        char result = '-';
        try{
            result = inputFromServer.readChar();
        } catch (Exception e) {e.printStackTrace();}
        return result;
    }

    private void receivePlay(){
        int[] play = new int[5];
        char player = ' ';
        int nextSquare = 0;
        try {
            String turn = (String) inputFromServer.readObject();
            String[] coords = turn.split("-");
            for(int i = 0; i < 4; i++){
                play[i] = Integer.parseInt(coords[i]);
            }
            player = coords[4].charAt(0);
            if(coords[5].equals("")){
                nextSquare = -1;
            } else {
                nextSquare = Integer.parseInt(coords[5]);
            }
        } catch (Exception e) {e.printStackTrace();}

        switch (player){
            case 'x':
                Renderer.getInstance().drawX(play[0], play[1], play[2], play[3]);
                Renderer.getInstance().underline('x');
                break;
            case 'o':
                Renderer.getInstance().drawO(play[0], play[1], play[2], play[3]);
                Renderer.getInstance().underline('o');
                break;
        }
        Renderer.getInstance().renderPlayableSquare(nextSquare);
    }

    private int translate(int x, int y){
        return x + y * 3;
    }

    private void receiveMessage(){
        try {
            String message = (String) inputFromServer.readObject();
            Renderer.getInstance().renderMessage(message);
        } catch (Exception e) {e.printStackTrace();}
    }
    private void colorNextSquare(){
        try{
            int nextSquare = inputFromServer.readInt();
            Renderer.getInstance().renderPlayableSquare(nextSquare);
        } catch (Exception e){e.printStackTrace();}
    }
    private void quitReceieved(){
        running = false;
    }
    private void gameWonAlert(){

    }

}
