package be;

import bll.util.Log;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Optional;

public class DisplayMessage {
    private static Log log = new Log();

    /**
     * Displays errormessages to the user.
     *
     * @param ex The Exception
     */
    public static void displayError(Exception ex) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            addImage(alert);
            alert.setTitle("Error: Something went wrong");
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(String.valueOf(ex.getCause()));
            alert.showAndWait();
            log.createLog(String.valueOf(ex.getCause()));
        });
    }

    public static boolean displayErrorSTOP(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        addImage(alert);
        alert.setTitle("Error: Something went wrong");
        alert.setHeaderText(ex.getMessage());
        alert.setContentText(String.valueOf(ex.getCause()));
        log.createLog(String.valueOf(ex.getCause()));
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean displayWarning (String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        addImage(alert);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Press OK to continue.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void displayMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            addImage(alert);
            alert.setTitle("Message");
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }

    private static void addImage(Alert alert){
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image image = new Image("/gui/Images/MyTunesLogo.png");
        stage.getIcons().add(image);
    }
}
