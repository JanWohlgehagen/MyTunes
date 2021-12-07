package be;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
            alert.showAndWait();
        });
    }

    public static boolean displayWarning (String Message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(Message);
        alert.setContentText("Press OK to continue.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}
