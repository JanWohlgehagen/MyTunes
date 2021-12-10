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
import java.io.IOException;

import static be.DisplayMessage.displayMessage;

public class NewPlaylistController {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;

    public void handleSaveBtn(ActionEvent actionEvent) throws MyTunesException {
        savePlaylist();
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    public void closeStage(){
        Stage stage = (Stage) anchorPaneId.getScene().getWindow();
        stage.close();
    }

    public void HandleEnterSave(KeyEvent keyEvent) throws MyTunesException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            savePlaylist();
        }
    }

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