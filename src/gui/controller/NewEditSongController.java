package gui.controller;

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
import java.net.URL;
import java.util.*;

public class NewEditSongController implements Initializable {

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
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ObservableList<String> categories;


    public NewEditSongController() {
        fileChooser = new FileChooser();
        categories = FXCollections.observableArrayList();
        cBoxCategory = new ComboBox();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }

    public void handleChooseBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) gridPaneId.getScene().getWindow();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music file", "*.mp3"),
                new FileChooser.ExtensionFilter("Music file", "*.wav"),
                new FileChooser.ExtensionFilter("Music file", "*.wave"));

        try {
            file = fileChooser.showOpenDialog(stage); // assigns the chosen file to the file
            fileChooser.setInitialDirectory(file.getParentFile()); //sets the initial file when the fileChooser is opened to the last known directory
        } catch (Exception ex){
           // TODO
        }

        if(file != null){ //DirectoryChooser returns null if the user closes the browse window
            txtFile.setText(file.getAbsolutePath().replace("\\", "/"));
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnReady(() -> {
                double durInSeconds = media.getDuration().toSeconds();
                int minutes = (int) durInSeconds / 60; // divide by 60 to get the minutes from seconds.
                int seconds = (int) durInSeconds % 60; // remaining seconds

                String strDurInMinutes = minutes + ":" + seconds;

                txtTime.setText(strDurInMinutes);
            });

        }

    }

    public void handleSaveBtn(ActionEvent actionEvent) {
        //TODO
    }

    //closes the stage
    public void handleCancelBtn(ActionEvent actionEvent) {
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
