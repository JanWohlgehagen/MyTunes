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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static be.DisplayMessage.displayMessage;

public class EditPlaylistController implements Initializable {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;
    private PlaylistModel playlistModel;
    private PlaylistListModel playlistListModel;
    private MainController mainController;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = new App().getController();
        playlistModel = mainController.getSelectedPlaylist();
        try {
            playlistListModel = new PlaylistListModel();

        } catch (MyTunesException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtName.setText(playlistModel.getNameProperty().get());
    }

    public void HandleEnterSave(KeyEvent keyEvent) throws MyTunesException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            savePlaylist();
        }
    }

    private void savePlaylist() throws MyTunesException {
        if(txtName.getText().isBlank()) {
            displayMessage("The name is empty");
        }else{
            playlistListModel.updatePlaylistToView(playlistModel, txtName.getText());
            closeStage();
        }
    }
}
