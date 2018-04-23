package Client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        primaryStage.setTitle("Ultimate Tic Tac Toe");

        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
