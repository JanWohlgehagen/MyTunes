package gui.controller;


import be.Playlist;
import be.Song;
import dal.DALException;
import gui.model.*;
import gui.util.SceneSwapper;
import gui.util.SongPlayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;

import java.util.ResourceBundle;

import static be.DisplayMessage.*;


public class MainController implements Initializable {

    public Button btnPause;
    @FXML
    private Label lblSongProgress;
    @FXML
    private Label lblSongDuration;
    @FXML
    private TableView<SongModel> tvSongsOnPlaylist;
    @FXML
    private TableColumn<SongModel, String> txtSongsInPlayList;

    @FXML
    private TableView<SongModel> tvSongs;
    @FXML
    private TableColumn<SongModel, String> tcTitle;
    @FXML
    private TableColumn<SongModel, String> tcArtist;
    @FXML
    private TableColumn<SongModel, String> tcCategory;
    @FXML
    private TableColumn<SongModel, String> tcTime;

    @FXML
    private TableView<PlaylistModel> tvPlaylists;
    @FXML
    private TableColumn<PlaylistModel, String> txtName;
    @FXML
    private TableColumn<PlaylistModel, Integer> txtSongs;
    @FXML
    private TableColumn<PlaylistModel, String> txtTime;

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
    @FXML
    private Slider progBar;

    private SceneSwapper sceneSwapper;
    private SongPlayer songPlayer;
    private SongModel currentlySong;
    private PlaylistModel playlistModel;
    private SongListModel songListModel;
    private PlaylistListModel playlistListModel;


    public MainController() throws DALException, IOException {

        try {
            progBar = new Slider();
            sceneSwapper = new SceneSwapper();
            songListModel = new SongListModel();
            playlistListModel = new PlaylistListModel();

        }catch (DALException DALex){
            if(displayErrorSTOP(DALex)){
                System.exit(0);
            }
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentlySong = new SongModel();

        tvSongsOnPlaylist.setPlaceholder(new Label("Select a playlist \n with songs"));

        //tvSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //{ de her kan slet nu
        playlistListModel.getSelectedPlayList().bind(tvPlaylists.getSelectionModel().selectedItemProperty());
        songListModel.getSelectedSong().bind(tvSongs.getSelectionModel().selectedItemProperty());
        playlistListModel.getSelectedSongInPlaylist().bind(tvSongsOnPlaylist.getSelectionModel().selectedItemProperty());
        //}

        // list of all songs
        tvSongs.setItems(songListModel.getSongs());
        tcTitle.setCellValueFactory(addSongToList -> addSongToList.getValue().getTitleProperty());
        tcArtist.setCellValueFactory(addSongToList -> addSongToList.getValue().getArtistProperty());
        tcCategory.setCellValueFactory(addSongToList -> addSongToList.getValue().getGenreProperty());
        tcTime.setCellValueFactory(addSongToList -> addSongToList.getValue().getDurationString());

        // list of all Playlists

        tvPlaylists.setItems(playlistListModel.getPlayLists());
        txtName.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getNameProperty());
        txtSongs.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTotalSongsProperty().asObject());
        txtTime.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getDurationStringProperty());

        // Search in all songs
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                songListModel.searchSong(newValue);
            } catch (DALException DALex) {
                DALex.printStackTrace();
                displayError(new DALException("Error: Something went wrong in the search engine"));
            }
        });

    }


    /**
     * switch the scene to a different scene.
     *
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */
    public void handleNewPlaylistBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "NewPlaylistView.fxml");
    }


    public void infoToNewPlaylist(String playlistName) throws DALException, IOException {
        playlistListModel.createPlaylist(playlistName);
    }

    /**
     * switch the scene to a different scene.
     *
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */

    public void handleEditPlaylistBtn(ActionEvent actionEvent) throws IOException, DALException {
        if(getSelectedPlaylist() == null){
            displayMessage("There is no playlist select, please select a playlist");
        }else {
            sceneSwapper.sceneSwitch(new Stage(), "EditPlaylistView.fxml");
        }
    }

    /**
     * deletes a playlist from the application
     *
     * @param actionEvent runs when an action is performed on the button.
     */

    public void handleDeletePlaylistBtn(ActionEvent actionEvent) throws DALException {
        if(getSelectedPlaylist() == null){
            displayMessage("There is no playlist select, please select a playlist");
        }else {
            if (displayWarning("This action will delete the playlist permanently.")) {
                playlistListModel.deletePlaylist(getSelectedPlaylist()); // Ask the model to remove remove a playlist
            }
        }
    }

    /**
     * moves a song up by one in the tableview.
     *
     * @param actionEvent runs when an action is called on the button
     */
    public void handleAscendSongInPlaylistBtn(ActionEvent actionEvent) {
        if(getSelectedSongFromPlaylist() == null){
            displayMessage("There is no song select, please select a song");
        }else {
            SongModel songModel = getSelectedSongFromPlaylist();
            PlaylistModel playlistModel = getSelectedPlaylist();
            playlistListModel.AscendSongInPlaylist(playlistModel, songModel);
        }
    }

    /**
     * moves a song down by one in the tableview.
     *
     * @param actionEvent runs when action is performed on the button.
     */
    public void handleDescendSongInPlaylistBtn(ActionEvent actionEvent) {
        if(getSelectedSongFromPlaylist() == null){
            displayMessage("There is no song select, please select a song");
        }else {
            SongModel songModel = getSelectedSongFromPlaylist();
            PlaylistModel playlistModel = getSelectedPlaylist();
            playlistListModel.DescendSongInPlaylist(playlistModel, songModel);
        }
    }

    /**
     * adds a song to a certain playlist
     * @param actionEvent runs when an action is performed on a button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) throws DALException {
        SongModel songModel = getSelectedSong();
        PlaylistModel playlistModel = getSelectedPlaylist();

        if(getSelectedSong() == null || getSelectedPlaylist() == null){
            displayMessage("There is no song or playlist select, please select a song or playlist");
        }else {
            try {
                playlistListModel.addSongToPlaylist(songModel.convertToSong(), playlistModel.convertToPlaylist());
                playlistModel.addSongToPlayList(songModel);
            } catch (DALException DALex) {
                displayError(new DALException("This song already exist in this playlist."));
            }
        }
    }

    /**
     * deletes a song from a playlist.
     *
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleDeleteSongInPlaylistBtn(ActionEvent actionEvent) throws DALException {
        if(getSelectedSongFromPlaylist() == null || getSelectedPlaylist() == null){
            displayMessage("There is no song or playlist select, please select a song or playlist");
        }else{
            if (displayWarning("This action will delete the song from the playlist.")) {
                SongModel songModel = getSelectedSongFromPlaylist();
                PlaylistModel playlistModel = getSelectedPlaylist();
                playlistListModel.removeSongFromPlaylist(songModel, playlistModel);
            }
        }
    }

    public void handleViewSongs(MouseEvent mouseEvent) throws DALException {
        if(getSelectedPlaylist() != null) {
            tvSongsOnPlaylist.setItems(getSelectedPlaylist().getListOfSongs());
            txtSongsInPlayList.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTitleProperty());
        }
        playlistModel = tvPlaylists.getSelectionModel().getSelectedItem();
    }

    /**
     * switches the scene over to NewEditSongView.fxml.
     *
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find stage.
     */
    public void handleNewSongBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "NewSongView.fxml");
    }

    /**
     * switches the scene over to NewEditSongView.fxml.
     *
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find the stage.
     */
    public void handleEditSongBtn(ActionEvent actionEvent) throws IOException {
        if(getSelectedSong() == null){
            displayMessage("There is no song select, please select a song");
        }else {
            sceneSwapper.sceneSwitch(new Stage(), "EditSongView.fxml");
        }
    }

    /**
     * removes a song
     *
     * @param actionEvent runs when an action is performed.
     */
    public void handleDeleteSongBtn(ActionEvent actionEvent) throws DALException {
        if(getSelectedSong() == null){
            displayMessage("There is no song select, please select a song");
        }else{
            if (displayWarning("This action will delete the song permanently.")) {
                songListModel.deleteSong(getSelectedSong());
            }
        }
    }

    public PlaylistModel getSelectedPlaylist(){
        return tvPlaylists.getSelectionModel().getSelectedItem();
    }

    public SongModel getSelectedSong() {
        return tvSongs.getSelectionModel().getSelectedItem();
    }

    public SongModel getSelectedSongFromPlaylist(){
        return tvSongsOnPlaylist.getSelectionModel().getSelectedItem();
    }

    public void infoToCreateSong(String title, String artist, String genre, double duration, String pathToFile) throws DALException {
        songListModel.createSong(title, artist, genre, duration,pathToFile);
    }

//____________________________________mediaPlayer___________________________________________

    /**
     * sets volume og the songs that will be played
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(MouseEvent dragEvent) {
        songPlayer.setVolume(sldVolume.getValue());
        System.out.println(sldVolume.getValue());
    }

    public void handleTvSongsInPlaylistClicked(MouseEvent mouseEvent) {
        tvSongs.getSelectionModel().clearSelection();
        lblCurrentSongPlaying.setText(tvSongsOnPlaylist.getSelectionModel().getSelectedItem().getTitleProperty().get());
    }

    public void handleTvSongClicked(MouseEvent mouseEvent) {
        tvSongsOnPlaylist.getSelectionModel().clearSelection();
        lblCurrentSongPlaying.setText(getSelectedSong().getTitleProperty().get());
    }

    public void handleProgBarPressed(MouseEvent event) {
        songPlayer.pause();
    }

    public void handleProgBarReleased(MouseEvent event) {
        songPlayer.getMediaPlayer().seek(Duration.seconds((progBar.getValue() / 100) * songPlayer.getMediaPlayer().getTotalDuration().toSeconds()));
        songPlayer.play(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex());
    }

    public void updateProgBar() {
        songPlayer.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            double totalSongDuration = songPlayer.getSongModel().getDurationProperty().get();
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
                                Duration newValue) {
                progBar.setValue((newValue.toSeconds() / songPlayer.getMediaPlayer().getTotalDuration().toSeconds()) * 100);
                lblSongProgress.setText(songPlayer.getSongModel().getDurationString(songPlayer.getMediaPlayer().getCurrentTime().toMillis()));
            }
        });
        lblSongDuration.setText(songPlayer.getSongModel().getDurationString().get());
    }

    public void updateIsPlayingLabel(String title) {
        lblCurrentSongPlaying.setText(title);
    }

    /**
     * skips current song to next song
     *
     * @param actionEvent when an action is performed on button program will run
     */
    public void handleNextSongBtn(ActionEvent actionEvent) throws DALException {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.nextSong();
        tvSongsOnPlaylist.getSelectionModel().select(songPlayer.getIndex());
        updateProgBar();
    }

    /**
     * goes back to previous song
     *
     * @param actionEvent will run when an action is called on the button
     */
    public void handlePreviousSongBtn(ActionEvent actionEvent) throws IOException, DALException {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.previousSong();
        tvSongsOnPlaylist.getSelectionModel().select(songPlayer.getIndex());
        updateProgBar();
    }

    /**
     * switches to picture of pause button and plays music either playing the old song or a new song.
     *
     * @param actionEvent runs when an action is performed on the button
     */
    public void handlePlayBtn(ActionEvent actionEvent) throws DALException, IOException {
        if(songPlayer == null){
            songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
        }
        else if(tvPlaylists.getSelectionModel().getSelectedItem().getIdProperty().get() != songPlayer.getId()){
            songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
        }
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        sldVolume.setDisable(false);
        progBar.setDisable(false);
        songPlayer.play(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex());
        updateProgBar();
    }

    /**
     * switches to the play button picture
     *
     * @param actionEvent
     */
    public void handlePauseBtn(ActionEvent actionEvent) {
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        songPlayer.pause();
        updateProgBar();
    }
}

