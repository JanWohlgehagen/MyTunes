package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewEditPlaylistController {

    @FXML
    private AnchorPane anchorPaneId;
    @FXML
    private TextField txtName;

    public void handleSaveBtn(ActionEvent actionEvent) {
        //TODO
    }

    public void handleCancelBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorPaneId.getScene().getWindow();
        stage.close();
    }
}
