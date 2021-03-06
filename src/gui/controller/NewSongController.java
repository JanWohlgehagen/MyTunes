package gui.controller;

import be.MyTunesException;
import gui.App;
import gui.model.SongListModel;
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
import java.util.*;

import static be.DisplayMessage.displayMessage;

/**
 * we use the controller for when a user wants to import a song to the program.
 */
public class NewSongController implements Initializable {

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
    private SongListModel songListModel;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ObservableList<String> categories;
    private String strDurInMinutes ="";


    /**
     * constructor of NewSongController, where we are creating new Objects.
     * @throws MyTunesException
     * @throws IOException
     */
    public NewSongController() throws MyTunesException, IOException {
        fileChooser = new FileChooser();
        categories = FXCollections.observableArrayList();
        cBoxCategory = new ComboBox();
        songListModel = new SongListModel();
    }

    /**
     * initialize the controller.
     * uses the setdata method.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }

    /**
     *  opens a chose window where you can choose the song you want to import into the program.
     * @param actionEvent runs when an actions in performed on the ChooseBtn.
     */
    public void handleChooseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) gridPaneId.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music file", "*.mp3", "*.wav", "*.wave"));
        file = fileChooser.showOpenDialog(stage); // assigns the chosen file to the file
        fileChooser.setInitialDirectory(file.getParentFile()); //sets the initial file when the fileChooser is opened to the last known directory

        if(file != null){ //DirectoryChooser returns null if the user closes the browse window
            txtFile.setText(file.getAbsolutePath().replace("\\", "/"));
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                double durInSeconds = media.getDuration().toSeconds();
                int minutes = (int) durInSeconds / 60; // divide by 60 to get the minutes from seconds.
                int seconds = (int) durInSeconds % 60; // remaining seconds

                strDurInMinutes = minutes + ":" + seconds;
                txtTime.setText(strDurInMinutes);
            });
        }
    }

    /**
     *  checks if the Enter key have been pressed, and calls the saveSong.
     * @param keyEvent runs when a key is pressed
     */
    public void HandleEnterSave(KeyEvent keyEvent) throws MyTunesException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            saveSong();
        }
    }

    /**
     * @param actionEvent runs when a actions in performed on the SaveBtn.
     */
    public void handleSaveBtn(ActionEvent actionEvent) throws MyTunesException {
        saveSong();
    }

    /**
     * @param actionEvent runs when a action is performed on the cancel button.
     */
    public void handleCancelBtn(ActionEvent actionEvent) {
        closeStage();
    }

    /**
     * closess the stage.
     */
    public void closeStage(){
        Stage stage = (Stage) gridPaneId.getScene().getWindow();
        stage.close();
    }

    /**
     * saves a song.
     * checks if all fields is filled in.
     * @throws MyTunesException
     */
    private void saveSong() throws MyTunesException {
        MainController mainController = new App().getController();
        if(txtArtist.getText().isBlank() || txtFile.getText().isBlank() || txtTitle.getText().isBlank() || cBoxCategory.getSelectionModel().getSelectedItem() == null){
            displayMessage("One of the field are empty");
        }else{
            mainController.infoToCreateSong(txtTitle.getText(), txtArtist.getText(), cBoxCategory.getSelectionModel().getSelectedItem().toString(), media.getDuration().toMillis(), txtFile.getText());
            closeStage();
        }
    }

    /**
     * sets the data into the dropbox.
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
