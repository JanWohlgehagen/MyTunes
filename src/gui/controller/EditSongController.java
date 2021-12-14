package gui.controller;

import be.MyTunesException;
import gui.App;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static be.DisplayMessage.displayError;
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
    private ObservableList<String> categories;
    SongListModel songListModel;
    SongModel songModel;

    /**
     * constructor of EditSongController.
     */
    public EditSongController() {
        fileChooser = new FileChooser();
        categories = FXCollections.observableArrayList();
        cBoxCategory = new ComboBox();

    }


    /**
     * initialize the stage
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            songModel = new SongModel();
            songListModel = new SongListModel();
        }catch (Exception ex){
            displayError(ex);
        }
        MainController mainController = new App().getController();
        songModel = mainController.getSelectedSong();
        txtTitle.setText(songModel.getTitleProperty().get());
        txtArtist.setText(songModel.getArtistProperty().get());
        txtTime.setText(songModel.getDurationStringProperty().get());
        txtFile.setText(songModel.getPathToFileProperty().get());
        cBoxCategory.getSelectionModel().select(songModel.getGenreProperty().get());
        setData();
    }

    /**
     * @param actionEvent runs when an action is performed on SaveBtn
     */
    public void handleSaveBtn(ActionEvent actionEvent) {
        saveSong();
    }

    /**
     * @param actionEvent runs when an action is performed on the CancelBtn button.
     */
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }


    /**
     * Checks if Enter is pressed, and then runs the saveSong.
     * @param keyEvent runs when a key is pressed.
     */
    public void HandleEnterSave(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            saveSong();
        }
    }

    /**
     * saves a song.
     * checks all textfields is not empty.
     */
    private void saveSong() {
        if(txtArtist.getText().isBlank() || txtTitle.getText().isBlank() || txtFile.getText().isBlank() || cBoxCategory.getSelectionModel().getSelectedItem() == null) {
            displayMessage("One or more field is empty");
        }else{
            try {
                songListModel.updateSongToView(songModel, txtTitle.getText(), txtArtist.getText(), cBoxCategory.getSelectionModel().getSelectedItem().toString());
                closeStage();
            }catch (MyTunesException MyTex){
                displayError(MyTex);
                MyTex.printStackTrace();
            }
        }
    }

    /**
     * closes the stage.
     */
    public void closeStage(){
        Stage stage = (Stage) gridPaneId.getScene().getWindow();
        stage.close();
    }

    /**
     * sets the data into the Dropbox.
     */
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
