package Server;

import java.io.*;
import java.net.Socket;

//X and O will each have a player connection created
public class PlayerConnection {

    private Socket connection;
    private ObjectInputStream inputFromServer;
    private ObjectOutputStream outputToServer;

    public PlayerConnection(String ip, int port) {
        connectToServer(ip, port);
    }

    private void connectToServer(String ip, int port){
        try{
            connection = new Socket(ip, port);
//            outputToServer = new ObjectOutputStream(connection.getOutputStream());
//            inputFromServer = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {e.printStackTrace();}
    }

}
