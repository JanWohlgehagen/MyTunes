package gui.controller;


import be.MyTunesException;
import gui.App;
import gui.model.PlaylistListModel;
import gui.model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import static be.DisplayMessage.displayError;
import static be.DisplayMessage.displayMessage;

public class EditPlaylistController implements Initializable {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;
    private PlaylistModel playlistModel;
    private PlaylistListModel playlistListModel;
    private MainController mainController;


    /**
     * @param actionEvent runs when an action is performed on the SaveBtn.
     */
    public void handleSaveBtn(ActionEvent actionEvent) {
        savePlaylist();
    }

    /**
     * @param actionEvent runs when an action is performed on the CancelBtn
     */
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    /**
     * closes the stage
     */
    public void closeStage(){
        Stage stage = (Stage) anchorPaneId.getScene().getWindow();
        stage.close();
    }


    /**
     * initialize the EditPlaylistController.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = new App().getController();
        playlistModel = mainController.getSelectedPlaylist();
        try {
            playlistListModel = new PlaylistListModel();

        } catch (MyTunesException MyTex) {
            displayError(MyTex);
        }
        txtName.setText(playlistModel.getNameProperty().get());
    }

    /**
     * checks for key Enter being pressed and runs method saveplaylist.
     * @param keyEvent runs when key being pressed
     */
    public void HandleEnterSave(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            savePlaylist();
        }
    }

    /**
     * saves a playlist change
     * checks a for text field not being empty
     */
    private void savePlaylist() {
        if(txtName.getText().isBlank()) {
            displayMessage("The name is empty");
        }else{
            try {
                playlistListModel.updatePlaylistToView(playlistModel, txtName.getText());
                closeStage();
            }catch (MyTunesException MyTex){
                displayError(MyTex);
            }
        }
    }
}
