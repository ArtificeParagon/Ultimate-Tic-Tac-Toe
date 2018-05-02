package Server;

//X and O will each have a player connection created
public class PlayerConnection {

    private final char playerToken;
    private boolean isLocal;

    public PlayerConnection(char token, boolean isLocal) {
        playerToken = token;
        this.isLocal = isLocal;
        connectToServer();
    }

    private void connectToServer(){
        if(isLocal){
            //TODO: connect to local server
        } else {
            //TODO: open prompt to connect to multiplayer
        }
    }

}
