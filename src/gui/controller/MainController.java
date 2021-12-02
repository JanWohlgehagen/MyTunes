package gui.controller;

import gui.model.ListModel;
import gui.model.PlaylistModel;
import gui.model.SongModel;
import gui.model.SongPlayerModel;
import gui.util.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController  implements Initializable {

    public Button btnPause;
    @FXML
    private TableView<PlaylistModel> tvSongsOnPlaylist;
    @FXML
    private TableView<SongModel> tvSongs;
    @FXML
    private TableView<PlaylistModel> tvPlaylists;
    @FXML
    private TableColumn<SongModel, String> tcTitle;
    @FXML
    private TableColumn<SongModel, String> tcArtist;
    @FXML
    private TableColumn<SongModel, String> tcCategory;
    @FXML
    private TableColumn<SongModel, Integer> tcTime;
    @FXML
    private TableColumn<PlaylistModel, String> txtName;
    @FXML
    private TableColumn<PlaylistModel, Integer>  txtSongs;
    @FXML
    private TableColumn<PlaylistModel, String>  txtTime;
    @FXML
    private Button btnPreviousSong;
    @FXML
    private Button btnAddSongToPlaylist;
    @FXML
    private Button btnAscendSongInPlaylist;
    @FXML
    private Button btnDescendSongInPlaylist;
    @FXML
    private Button btnSkipSong;
    @FXML
    private Button btnPlay;
    @FXML
    private TextField txtSearch;
    @FXML
    private Slider sldVolume;
    @FXML
    private Label lblCurrentSongPlaying;



    private final SceneSwapper sceneSwapper;
    private ListModel listModel;
    SongPlayerModel songPlayerModel = new SongPlayerModel("C:/Users/Magnus Overgaard/Downloads/bip.mp3");

    public MainController(){
        sceneSwapper = new SceneSwapper();
        listModel = new ListModel();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sldVolume.setValue(songPlayerModel.getSongVolume() * 100); // 100 a hundred because method get volume is between 0.0 and 1.0
        //just sets the volume slider default starting value to be the same as the songs' volume.

        // list of all songs
    tvSongs.setItems(listModel.getSongs());
    tcTitle.setCellValueFactory(addSongToList -> addSongToList.getValue().getTitleProperty());
    tcArtist.setCellValueFactory(addSongToList -> addSongToList.getValue().getArtistProperty());
    tcCategory.setCellValueFactory(addSongToList -> addSongToList.getValue().getGenreProperty());
    tcTime.setCellValueFactory(addSongToList -> addSongToList.getValue().getTimeProperty().asObject());

        // list of all Playlists
    tvPlaylists.setItems(listModel.getPlayLists());
    txtName.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getNameProperty());
    txtSongs.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getSongsProperty().asObject());
    txtTime.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTimeProperty());

        // Search in all songs
    txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
        listModel.searchSong(newValue);
    });

    }

    /**
     * sets volume og the songs that will be played
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(MouseEvent dragEvent) {
        System.out.println(sldVolume.getValue());
        songPlayerModel.setSongVolume(sldVolume.getValue());

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
     * switches to picture of pause button
     * @param actionEvent runs when an action is performed on the button
     */
    public void handlePlayBtn(ActionEvent actionEvent) {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);



        songPlayerModel.playSong();
    }

    /**
     * switches to the play button picture
     * @param actionEvent
     */
    public void handlePauseBtn(ActionEvent actionEvent) {
        songPlayerModel.pauseSong();
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
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
        sceneSwapper.sceneSwitch(new Stage(), "NewEditPlaylistView.fxml");
    }
    /**
     * switch the scene to a different scene.
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */
    public void HandleEditPlaylistBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(),"NewEditPlaylistView.fxml");
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
        sceneSwapper.sceneSwitch(new Stage(), "NewEditSongView.fxml");
    }
    /**
     *  switches the scene over to NewEditSongView.fxml.
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find the stage.
     */
    public void handleEditSongBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "NewEditSongView.fxml");
    }

    /**
     * removes a song
     * @param actionEvent runs when an action is performed.
     */
    public void HandleDeleteSongBtn(ActionEvent actionEvent) {
    }



}

