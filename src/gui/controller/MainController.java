package gui.controller;


import dal.DALException;
import gui.model.ListModel;
import gui.model.PlayListSongModel;
import gui.model.PlaylistModel;
import gui.model.SongModel;
import gui.util.SceneSwapper;
import gui.util.SongPlayer;
import javafx.application.Platform;

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
    private TableView<PlayListSongModel> tvSongsOnPlaylist;
    @FXML
    private TableColumn<PlayListSongModel, String> txtSongsInPlayList;

    @FXML
    private TableView<SongModel> tvSongs;
    @FXML
    private TableColumn<SongModel, String> tcTitle;
    @FXML
    private TableColumn<SongModel, String> tcArtist;
    @FXML
    private TableColumn<SongModel, String> tcCategory;
    @FXML
    private TableColumn<SongModel, Integer> tcTime;

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



    private SceneSwapper sceneSwapper;
    private ListModel listModel;
    SongPlayer songPlayer = new SongPlayer("songs/bip.mp3");

    public MainController() throws DALException, IOException {

        try {
            sceneSwapper = new SceneSwapper();
            listModel = new ListModel();
        }catch (DALException DALex){
            displayError(DALex);
            System.exit(0);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sldVolume.setValue(songPlayer.getVolume()*100);

        tvSongsOnPlaylist.setPlaceholder(new Label("Select a playlist \n with songs"));

        listModel.getSelectedPlayList().bind(tvPlaylists.getSelectionModel().selectedItemProperty());
        //tvSongs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listModel.getSelectedSong().bind(tvSongs.getSelectionModel().selectedItemProperty());

        // list of all songs
        tvSongs.setItems(listModel.getSongs());
        tcTitle.setCellValueFactory(addSongToList -> addSongToList.getValue().getTitleProperty());
        tcArtist.setCellValueFactory(addSongToList -> addSongToList.getValue().getArtistProperty());
        tcCategory.setCellValueFactory(addSongToList -> addSongToList.getValue().getGenreProperty());
        tcTime.setCellValueFactory(addSongToList -> addSongToList.getValue().getTimeProperty().asObject());

        // list of all Playlists

        tvPlaylists.setItems(listModel.getPlayLists());
        txtName.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getNameProperty());
        txtSongs.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTotalSongsProperty().asObject());
        txtTime.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTimeProperty());

        // Search in all songs
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {listModel.searchSong(newValue);});

    }

    public void addPlaylist(String playlistName) throws DALException {
        listModel.addPlaylistToView(playlistName);
    }

    /**
     * Displays errormessages to the user.
     *
     * @param ex The Exception
     */
    private void displayError(Exception ex) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong");
            alert.setHeaderText(ex.getMessage());
            alert.showAndWait();
        });
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
    public void handleNextSongBtn(ActionEvent actionEvent) {
    }

    /**
     * goes back to previous song
     * @param actionEvent will run when an action is called on the button
     */
    public void handlePreviousSongBtn(ActionEvent actionEvent) {
    }

    /**
     * switches to picture of pause button
     * @param actionEvent runs when an action is performed on the button
     */
    public void handlePlayBtn(ActionEvent actionEvent) {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.playSong();
    }

    /**
     * switches to the play button picture
     * @param actionEvent
     */
    public void handlePauseBtn(ActionEvent actionEvent) {
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        songPlayer.pauseMusic();
    }

    /**
     * adds a song to a certain playlist
     * @param actionEvent runs when an action is performed on a button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) throws DALException {
        SongModel songModel = listModel.getSelectedSong().getValue();
        PlaylistModel playlistModel = listModel.getSelectedPlayList().getValue();

        listModel.addSongToPlatlist(songModel.getIdProperty().get(), playlistModel.getIdProperty().get());

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
    public void handleEditPlaylistBtn(ActionEvent actionEvent) throws IOException {
        sceneSwapper.sceneSwitch(new Stage(),"EditPlaylistView.fxml");
    }

    /**
     * deletes a playlist from the application
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleDeletePlaylistBtn(ActionEvent actionEvent) {
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
    public void handleDeleteSongBtn(ActionEvent actionEvent) {
    }


    public void handleViewSongs(MouseEvent mouseEvent) throws DALException {
        tvSongsOnPlaylist.setItems(listModel.getPlayListSongs());
        txtSongsInPlayList.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTitleProperty());

    }


}

