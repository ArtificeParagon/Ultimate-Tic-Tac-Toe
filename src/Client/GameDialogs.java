package Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.Optional;

public class GameDialogs {

    public static int playChoice(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select an option");
        alert.setHeaderText("How would you like to play today?");
        alert.setContentText("Please select whether you would like to host a game, join a game someone" +
                " else is hosting, or if you would like to player a local game.");

        ButtonType localGame = new ButtonType("Local");
        ButtonType joinGame = new ButtonType("Join");
        ButtonType hostGame = new ButtonType("Host");

        alert.getButtonTypes().addAll(localGame, joinGame, hostGame);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == localGame){
            return 0;
        } else if(result.get() == joinGame){
            return 2;
        }else /*hostGame*/{
            return 1;
        }
    }

    public static Optional<Pair<String, String>> connect(ActionEvent event){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Connect");
        dialog.setHeaderText("Enter IP and port to connect to");

        ButtonType connectButton = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(connectButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField ipAddress = new TextField();
        ipAddress.setPromptText("IP Address");
        TextField port = new TextField();
        port.setPromptText("Port");

        grid.add(new Label("IP Address: "), 0, 0);
        grid.add(ipAddress, 1, 0);
        grid.add(new Label("Port: "), 0, 1);
        grid.add(port, 1, 1);
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> ipAddress.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == connectButton)
                return new Pair<>(ipAddress.getText(), port.getText());
            else
                return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        return result;
    }

}
