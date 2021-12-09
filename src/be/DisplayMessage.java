package be;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Optional;

public class DisplayMessage {

    /**
     * Displays errormessages to the user.
     *
     * @param ex The Exception
     */
    public static void displayError(Exception ex) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Something went wrong");
            alert.setHeaderText(ex.getMessage());
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image image = new Image("/gui/Images/MyTunesLogo.png");
            stage.getIcons().add(image);
            alert.showAndWait();
        });
    }

    public static boolean displayErrorSTOP(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error: Something went wrong");
        alert.setHeaderText(ex.getMessage());
        alert.setContentText(String.valueOf(ex.getCause()));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image image = new Image("/gui/Images/MyTunesLogo.png");
        stage.getIcons().add(image);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean displayWarning (String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Press OK to continue.");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image image = new Image("/gui/Images/MyTunesLogo.png");
        stage.getIcons().add(image);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void displayMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            Image image = new Image("/gui/Images/MyTunesLogo.png");
            stage.getIcons().add(image);
            alert.setHeaderText(message);
            alert.showAndWait();
        });
    }
}
