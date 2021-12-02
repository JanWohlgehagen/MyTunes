package gui.controller;

import dal.DALException;
import gui.model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewEditPlaylistController {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;
    private PlaylistModel playlistModel;
    private MainController mainController;

    public NewEditPlaylistController() throws IOException, DALException {
        playlistModel = new PlaylistModel();
        mainController = new MainController();
    }

    public void handleSaveBtn(ActionEvent actionEvent) throws DALException {
        playlistModel.createPlaylist(txtName.getText());
        closeStage();
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    public void closeStage(){
        Stage stage = (Stage) anchorPaneId.getScene().getWindow();
        stage.close();
    }
}
