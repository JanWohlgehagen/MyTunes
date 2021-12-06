package gui.controller;


import dal.DALException;
import gui.App;
import gui.model.ListModel;
import gui.model.PlaylistListModel;
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
    private PlaylistListModel playlistListModel;
    private MainController mainController;
    private ListModel listModel;


    public void handleSaveBtn(ActionEvent actionEvent) throws DALException {
        if(!txtName.getText().isBlank()) {
            playlistListModel.updatePlaylistToView(playlistModel, txtName.getText());
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
        mainController = new App().getController();
        playlistModel = mainController.getSelectedPlaylist();
        try {
            playlistListModel = new PlaylistListModel();

        } catch (DALException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtName.setText(playlistModel.getNameProperty().get());
    }
}
