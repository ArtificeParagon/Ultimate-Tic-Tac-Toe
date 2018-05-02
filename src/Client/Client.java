package Client;

import Game.Renderer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Client extends Application {

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
    }

    private void handleClick(double x, double y, double boardWidth){
        // handle if it is a click on the board else it isn't
        if(x < boardWidth){
            int boardX, boardY, pieceX, pieceY;
            boardX = (int) (x / (boardWidth/3));
            boardY = (int) (y / (boardWidth/3));
            pieceX = (int) ((x / (boardWidth/9)) % 3);
            pieceY = (int) ((y / (boardWidth/9)) % 3);
            System.out.println("BoardX: " + boardX + ", BoardY: " + boardY);
            System.out.println("PieceX: " + pieceX + ", PieceY: " + pieceY);
        }
    }

}
