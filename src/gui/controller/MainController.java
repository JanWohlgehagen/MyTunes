package gui.controller;

import gui.model.SongModel;
import gui.view.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController  implements Initializable {

    @FXML
    private TextField txtSearch;
    @FXML
    private Slider sldVolume;
    @FXML
    private TableView tvSongsOnPlaylist;
    @FXML
    private TableView tvSongs;
    @FXML
    private Label lblCurrentSongPlaying;
    @FXML
    private Button btnPlayPause;
    @FXML
    private TableView tvPlaylists;

    private final SceneSwapper sceneSwapper;
    private SongModel songModel;

    public MainController(){
        sceneSwapper = new SceneSwapper();
        songModel = new SongModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {songModel.searchSong(newValue);});

    }

    /**
     * sets volume og the songs that will be played
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(DragEvent dragEvent) {
    }

    /**
     * skips current song to next song
     * @param actionEvent when an action is performed on button program will run
     */
    public void handleNextSongBtn(ActionEvent actionEvent) {
    }

    /**
     * goes back to previous song
     * @param actionEvent will run when an action is called on the button
     */
    public void HandlePreviousSongBtn(ActionEvent actionEvent) {
    }

    /**
     * switches between pausing and playing music
     * @param actionEvent runs when an action is performed on the button
     */
    public void handlePlayPauseBtn(ActionEvent actionEvent) {
    }

    /**
     * adds a song to a certain playlist
     * @param actionEvent runs when an action is performed on a button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) {
    }

    /**
     * switch the scene to a different scene.
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */
    public void HandleNewPlaylistBtn(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        sceneSwapper.sceneSwitch(window, "NewEditPlaylistView.fxml");
    }
    /**
     * switch the scene to a different scene.
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */
    public void HandleEditPlaylistBtn(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        sceneSwapper.sceneSwitch(window, "NewEditPlaylistView.fxml");
    }

    /**
     * deletes a playlist from the application
     * @param actionEvent runs when an action is performed on the button.
     */
    public void HandleDeletePlaylistBtn(ActionEvent actionEvent) {
        tvPlaylists.getItems().remove(tvPlaylists.getSelectionModel().getSelectedItem()); // fjerner kun sangen fra tableview
    }


    /**
     * moves a song up by one in the tableview.
     * @param actionEvent runs when an action is called on the button
     */
    public void handleAscendSongInPlaylistBtn(ActionEvent actionEvent) {
     tvSongsOnPlaylist.getItems().set(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex() + 1,tvSongsOnPlaylist.getSelectionModel().getSelectedItem());
    }

    /**
     * moves a song down by one in the tableview.
     * @param actionEvent runs when action is performed on the button.
     */
    public void handleDescendSongInPlaylistBtn(ActionEvent actionEvent) {
        tvSongsOnPlaylist.getItems().set(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex() - 1,tvSongsOnPlaylist.getSelectionModel().getSelectedItem());
    }

    /**
     * deletes a song from a playlist.
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleDeleteSongInPlaylistBtn(ActionEvent actionEvent) {
    }

    /**
     *  switches the scene over to NewEditSongView.fxml.
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find stage.
     */
    public void handleNewSongBtn(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        sceneSwapper.sceneSwitch(window, "NewEditSongView.fxml");
    }
    /**
     *  switches the scene over to NewEditSongView.fxml.
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find the stage.
     */
    public void handleEditSongBtn(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        sceneSwapper.sceneSwitch(window, "NewEditSongView.fxml");
    }

    /**
     * removes a song
     * @param actionEvent runs when an action is performed.
     */
    public void HandleDeleteSongBtn(ActionEvent actionEvent) {
    }


}

