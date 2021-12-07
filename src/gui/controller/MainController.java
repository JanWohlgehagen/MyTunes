package gui.controller;


import be.Song;
import dal.DALException;
import gui.model.*;
import gui.util.SceneSwapper;
import gui.util.SongPlayer;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static be.DisplayMessage.displayError;
import static be.DisplayMessage.displayWarning;


public class MainController  implements Initializable {

    public Button btnPause;
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


    private SongModel songModel;
    private PlaylistModel playlistModel;
    private SceneSwapper sceneSwapper;

    private SongPlayer songPlayer;

    private Song currentlySong;
    private int playListId;
    PlayListSongModel playListSongModel = new PlayListSongModel(null);
    private SongListModel songListModel;
    private PlaylistListModel playlistListModel;


    public MainController() throws DALException, IOException {

        try {
            sceneSwapper = new SceneSwapper();
            songListModel = new SongListModel();
            playlistListModel = new PlaylistListModel();

        }catch (DALException DALex){
            displayError(DALex);
            System.exit(0);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvSongsOnPlaylist.setPlaceholder(new Label("Select a playlist \n with songs"));

        playlistListModel.getSelectedPlayList().bind(tvPlaylists.getSelectionModel().selectedItemProperty());
        //tvSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        songListModel.getSelectedSong().bind(tvSongs.getSelectionModel().selectedItemProperty());

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

    public void addPlaylist(String playlistName) throws DALException, IOException {
        playlistListModel.addPlaylistToView(playlistName);
    }

    /**
     * sets volume og the songs that will be played
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(MouseEvent dragEvent) {
        songPlayer.setVolume(sldVolume.getValue());
        System.out.println(sldVolume.getValue());
    }

    /**
     * skips current song to next song
     * @param actionEvent when an action is performed on button program will run
     */
    public void handleNextSongBtn(ActionEvent actionEvent) throws DALException {
        songPlayer.pauseMusic();

        currentlySong = playListSongModel.skipSong(currentlySong, playListId);
        songPlayer = new SongPlayer(currentlySong.getPathToFile());
        lblCurrentSongPlaying.setText(currentlySong.getTitle() + ": is Playing" );
        songPlayer.playSong();
    }

    /**
     * goes back to previous song
     * @param actionEvent will run when an action is called on the button
     */
    public void handlePreviousSongBtn(ActionEvent actionEvent) throws IOException, DALException {
        songPlayer.pauseMusic();

        currentlySong = playListSongModel.previousSong(currentlySong, playListId);
        songPlayer = new SongPlayer(currentlySong.getPathToFile());
        lblCurrentSongPlaying.setText(currentlySong.getTitle() + ": is Playing" );

        songPlayer.playSong();
    }

    /**
     * switches to picture of pause button
     * @param actionEvent runs when an action is performed on the button
     */
    public void handlePlayBtn(ActionEvent actionEvent) throws DALException, IOException {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);



        playListId = tvPlaylists.getSelectionModel().getSelectedItem().getIdProperty().intValue();
        currentlySong = playListSongModel.playCurrentSong(tvSongsOnPlaylist.getSelectionModel().getSelectedItem().getTitleProperty().toString(),playListId);
        lblCurrentSongPlaying.setText(currentlySong.getTitle() + ": is Playing" );

        songPlayer = new SongPlayer(currentlySong.getPathToFile());
        songPlayer.playSong();

    }

    /**
     * switches to the play button picture
     * @param actionEvent
     */
    public void handlePauseBtn(ActionEvent actionEvent) {
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        lblCurrentSongPlaying.setText(currentlySong.getTitle() + ": is Paused" );
        songPlayer.pauseMusic();
    }

    /**
     * adds a song to a certain playlist
     * @param actionEvent runs when an action is performed on a button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) throws DALException {
        SongModel songModel = songListModel.getSelectedSong().getValue();
        PlaylistModel playlistModel = playlistListModel.getSelectedPlayList().getValue();
        
        try{
            playlistListModel.addSongToPlaylist(songModel.convertToSong(), playlistModel.convertToPlaylist());
        }catch (DALException DALex){
            displayError(DALex);
            throw new DALException("This song already exist in this playlist.");
        }
        playlistModel.addSongToPlayList(songModel);
    }


    /**
     * switch the scene to a different scene.
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */
    public void handleNewPlaylistBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "NewPlaylistView.fxml");
    }
    /**
     * switch the scene to a different scene.
     * @param actionEvent runs when an action happens on a button.
     * @throws IOException if cant find stage.
     */

    public void handleEditPlaylistBtn(ActionEvent actionEvent) throws IOException, DALException {
        sceneSwapper.sceneSwitch(new Stage(), "EditPlaylistView.fxml");
    }

    /**
     * deletes a playlist from the application
     * @param actionEvent runs when an action is performed on the button.
     */

    public void handleDeletePlaylistBtn(ActionEvent actionEvent) throws DALException {
        if (displayWarning("This action will delete the playlist permanently.")){
            playlistListModel.deletePlaylist(playlistListModel.getSelectedPlayList().get()); // Ask the model to remove remove a playlist
        }


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
    public void handleDeleteSongInPlaylistBtn(ActionEvent actionEvent) throws DALException {
        if (displayWarning("This action will delete the song from the playlist.")) {
            SongModel songModel = tvSongsOnPlaylist.getSelectionModel().getSelectedItem();
            PlaylistModel playlistModel = playlistListModel.getSelectedPlayList().getValue();
            playlistListModel.removeSongFromPlaylist(songModel, playlistModel);
        }
    }

    /**
     *  switches the scene over to NewEditSongView.fxml.
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find stage.
     */
    public void handleNewSongBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "NewSongView.fxml");
    }
    /**
     *  switches the scene over to NewEditSongView.fxml.
     * @param actionEvent runs when an action is performed on the button.
     * @throws IOException if cant find the stage.
     */
    public void handleEditSongBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(), "EditSongView.fxml");
    }

    /**
     * removes a song
     * @param actionEvent runs when an action is performed.
     */
    public void handleDeleteSongBtn(ActionEvent actionEvent) throws DALException {
        if (displayWarning("This action will delete the song permanently.")) {
            songListModel.deleteSong(songListModel.getSelectedSong().get());
        }
    }


    public void handleViewSongs(MouseEvent mouseEvent) throws DALException {

        tvSongsOnPlaylist.setItems(playlistListModel.getSelectedPlayList().getValue().getListOfSongs());
        txtSongsInPlayList.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTitleProperty());
    }


    public PlaylistModel getSelectedPlaylist(){
        return tvPlaylists.getSelectionModel().getSelectedItem();
    }

    public void createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException {
        songListModel.createSong(title, artist, genre, duration,pathToFile);
    }

    public SongModel getSelectedSong() {
        return tvSongs.getSelectionModel().getSelectedItem();
    }


}

