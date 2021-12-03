package gui.controller;

import dal.DALException;
import gui.App;
import gui.model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPlaylistController implements Initializable {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;
    private PlaylistModel playlistModel;


    public void handleSaveBtn(ActionEvent actionEvent) throws DALException {
        if(!txtName.getText().isBlank()) {
            playlistModel.updatePlaylist(txtName.getText());
            closeStage();
        }
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
        MainController mainController = new App().getController();
        txtName.setText(mainController.getPlaylist().getNameProperty().get());
        try {
            playlistModel = new PlaylistModel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DALException e) {
            e.printStackTrace();
        }
    }
}
