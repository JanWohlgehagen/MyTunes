package gui.controller;

import be.MyTunesException;
import gui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static be.DisplayMessage.displayMessage;

public class NewPlaylistController {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;

    /**
     * @param actionEvent runs when an action in performed on the saveBtn button.
     * @throws MyTunesException
     */
    public void handleSaveBtn(ActionEvent actionEvent) throws MyTunesException {
        savePlaylist();
    }

    /**
     * @param actionEvent runs when an action is performed on CancelBtn Button.
     */
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    /**
     * closes the stage.
     */
    public void closeStage(){
        Stage stage = (Stage) anchorPaneId.getScene().getWindow();
        stage.close();
    }

    /**
     * checks if the Enter key has been pressed.
     * @param keyEvent runs when a ket is pressed.
     * @throws MyTunesException
     */
    public void HandleEnterSave(KeyEvent keyEvent) throws MyTunesException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            savePlaylist();
        }
    }

    /**
     * Saves a new playlist.
     * checks for text field isnt empty.
     * @throws MyTunesException
     */
    private void savePlaylist() throws MyTunesException {
        if(txtName.getText().isBlank()){
            displayMessage("The name is empty, please add a name");
        }else{
            MainController mainController = new App().getController();
            mainController.infoToNewPlaylist(txtName.getText());
            closeStage();
        }
    }
}