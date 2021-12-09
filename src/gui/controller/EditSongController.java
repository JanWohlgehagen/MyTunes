package gui.controller;

import dal.DALException;
import gui.App;
import gui.model.PlaylistListModel;
import gui.model.SongListModel;
import gui.model.SongModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static be.DisplayMessage.displayMessage;

public class EditSongController implements Initializable {

    @FXML
    private GridPane gridPaneId;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtFile;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtArtist;
    @FXML
    private ComboBox cBoxCategory;

    final FileChooser fileChooser;
    private SongModel songModel;
    private ObservableList<String> categories;
    private MainController mainController;
    SongListModel songListModel;

    public EditSongController() throws DALException, IOException {
        fileChooser = new FileChooser();
        categories = FXCollections.observableArrayList();
        cBoxCategory = new ComboBox();
        songModel = new SongModel();
        songListModel = new SongListModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainController mainController = new App().getController();
        songModel = mainController.getSelectedSong();
        try {
            SongListModel songListModel = new SongListModel();

        } catch (DALException | IOException e) {
            e.printStackTrace();
        }
        txtTitle.setText(songModel.getTitleProperty().get());
        txtArtist.setText(songModel.getArtistProperty().get());
        txtTime.setText(songModel.getDurationString().get());
        txtFile.setText(songModel.getPathToFileProperty().get());
        cBoxCategory.getSelectionModel().select(songModel.getGenreProperty().get());
        setData();
    }

    public void handleChooseBtn(ActionEvent actionEvent) {
        //Browse should not be available when editing a song.
    }

    public void handleSaveBtn(ActionEvent actionEvent) throws DALException {
        saveSong();
    }

    //closes the stage
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    public void HandleEnterSave(KeyEvent keyEvent) throws DALException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            saveSong();
        }
    }

    private void saveSong() throws DALException {
        if(txtArtist.getText().isBlank() || txtTitle.getText().isBlank() || txtFile.getText().isBlank() || cBoxCategory.getSelectionModel().getSelectedItem() == null) {
            displayMessage("One or more field is empty");
        }else{
            songListModel.updateSongToView(songModel, txtTitle.getText(), txtArtist.getText(), cBoxCategory.getSelectionModel().getSelectedItem().toString());
            closeStage();
        }
    }

    public void closeStage(){
        Stage stage = (Stage) gridPaneId.getScene().getWindow();
        stage.close();
    }

    private void setData() {
        categories.add("Pop");
        categories.add("Hip hop");
        categories.add("Rock");
        categories.add("Rhythm and blues");
        categories.add("Soul music");
        categories.add("Reggae");
        categories.add("Country");
        categories.add("Funk");
        categories.add("Folk music");
        categories.add("Middle Eastern");
        categories.add("Jazz");
        categories.add("Disco");
        categories.add("Electronic");
        categories.add("Classical");
        categories.add("Latin America");
        categories.add("Children's music");
        categories.add("Vocal");
        Collections.sort(categories);

        cBoxCategory.getItems().setAll(categories);
    }
}
