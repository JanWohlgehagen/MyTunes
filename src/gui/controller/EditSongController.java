package gui.controller;

import dal.DALException;
import gui.model.SongModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ObservableList<String> categories;
    private String strDurInMinutes ="";


    public EditSongController() throws DALException, IOException {
        fileChooser = new FileChooser();
        categories = FXCollections.observableArrayList();
        cBoxCategory = new ComboBox();
        songModel = new SongModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }

    public void handleChooseBtn(ActionEvent actionEvent) {
        //Browse should not be available when editing a song.
    }

    public void handleSaveBtn(ActionEvent actionEvent) throws DALException, IOException {
        if(!txtArtist.getText().isBlank() && !txtTime.getText().isBlank() && !txtFile.getText().isBlank() && !txtTitle.getText().isBlank() && cBoxCategory.getSelectionModel().getSelectedItem() != null){
            //TODO
            closeStage();
        }
    }

    //closes the stage
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
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
