package gui.controller;

import dal.DALException;
import gui.App;
import gui.model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPlaylistController {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;

    public void handleSaveBtn(ActionEvent actionEvent) throws DALException {
        MainController mainController = new App().getController();
        mainController.addPlaylist(txtName.getText());
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
