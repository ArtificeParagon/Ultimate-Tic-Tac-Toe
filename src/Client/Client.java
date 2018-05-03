package Client;

import Game.Renderer;
import Server.HostServer;
import Server.PlayerConnection;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.net.Socket;
import java.util.Optional;

public class Client extends Application {

    private HostServer server;
    private PlayerConnection solo;
    private PlayerConnection multi;

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        primaryStage.setTitle("Ultimate Tic Tac Toe");

        Canvas canvas = new Canvas();
        canvas.setWidth(1068);
        canvas.setHeight(720);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Renderer.getInstance(gc).drawBoard();
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleClick(event.getX(), event.getY(), canvas.getHeight());
            }
        });

        root.getChildren().addAll(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        determinePlayMethod(primaryStage);
    }

    public void determinePlayMethod(Stage stage){
        int choice = GameDialogs.playChoice();

        //0 is local, 1 is hosting, 2 is joining.
        switch (choice){
            case 1:
                startServer();
                stage.setOnCloseRequest((WindowEvent event) -> {
                    server.shutdown();
                });
                solo = new PlayerConnection("localhost", server.getPort());
                break;
            case 2:
                connectToServer();
                break;
            case 0:
                startServer();
                stage.setOnCloseRequest((WindowEvent event) -> {
                    server.shutdown();
                });
                solo = new PlayerConnection("localhost", server.getPort());
                multi = new PlayerConnection("localhost", server.getPort());
                break;
        }
    }


    public void startServer(){
        server = new HostServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
    public void connectToServer(){
        Optional<Pair<String, String>> ipPort = GameDialogs.connect(null);
        ipPort.ifPresent(result -> {
            solo = new PlayerConnection(result.getKey(), Integer.parseInt(result.getValue()));
        });
    }

    private void handleClick(double x, double y, double boardWidth){
        // handle if it is a click on the board else it isn't
        if(x < boardWidth){
            int boardX, boardY, pieceX, pieceY;
            boardX = (int) (x / (boardWidth/3));
            boardY = (int) (y / (boardWidth/3));
            pieceX = (int) ((x / (boardWidth/9)) % 3);
            pieceY = (int) ((y / (boardWidth/9)) % 3);

            solo.sendPlay(boardX, boardY, pieceX, pieceY);
        }
    }

}
