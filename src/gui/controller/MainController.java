package gui.controller;

import be.MyTunesException;
import gui.model.PlaylistListModel;
import gui.model.PlaylistModel;
import gui.model.SongListModel;
import gui.model.SongModel;
import gui.util.SceneSwapper;
import gui.util.SongPlayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static be.DisplayMessage.*;

public class MainController implements Initializable {

    public ToggleButton btnToggleShuffle;
    public Button btnSøg;
    @FXML
    private Label lblSongProgress;
    @FXML
    private Label lblSongDuration;
    @FXML
    private TableView<SongModel> tvSongsOnPlaylist;
    @FXML
    private TableColumn<SongModel, String> tcSongsInPlayList;

    @FXML
    private TableView<SongModel> tvSongs;
    @FXML
    private TableColumn<SongModel, String> tcTitle;
    @FXML
    private TableColumn<SongModel, String> tcArtist;
    @FXML
    private TableColumn<SongModel, String> tcCategory;
    @FXML
    private TableColumn<SongModel, String> tcTimeInSong;

    @FXML
    private TableView<PlaylistModel> tvPlaylists;
    @FXML
    private TableColumn<PlaylistModel, String> tcName;
    @FXML
    private TableColumn<PlaylistModel, Integer> tcSongs;
    @FXML
    private TableColumn<PlaylistModel, String> tcTimeInPlaylist;

    @FXML
    private Button btnPreviousSong;
    @FXML
    private Button btnSkipSong;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPause;
    @FXML
    private Button btnVolume;
    @FXML
    private TextField txtSearch;
    @FXML
    private Slider sldVolume;
    @FXML
    private Label lblCurrentSongPlaying;
    @FXML
    private Slider progressBar;
    @FXML
    private HBox hboxVolume;

    private SceneSwapper sceneSwapper;
    private SongPlayer songPlayer;
    private PlaylistModel playlistModel;
    private SongListModel songListModel;
    private PlaylistListModel playlistListModel;


    /**
     * constructor of the controller. where we create new objects.
     * @throws IOException
     */
    public MainController() throws IOException {
        try {
            progressBar = new Slider();
            sceneSwapper = new SceneSwapper();
            songListModel = new SongListModel();
            playlistListModel = new PlaylistListModel();
        } catch (MyTunesException DALex) {
            if (displayErrorSTOP(DALex)) {
                System.exit(0);
            }
        }
    }

    /**
     *  initialize our program.
     *  TODO hvad skal der stå her.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvSongsOnPlaylist.setPlaceholder(new Label("Select a playlist \n with songs"));

        // list of all songs
        tvSongs.setItems(songListModel.getSongs());
        tcTitle.setCellValueFactory(addSongToList -> addSongToList.getValue().getTitleProperty());
        tcArtist.setCellValueFactory(addSongToList -> addSongToList.getValue().getArtistProperty());
        tcCategory.setCellValueFactory(addSongToList -> addSongToList.getValue().getGenreProperty());
        tcTimeInSong.setCellValueFactory(addSongToList -> addSongToList.getValue().getDurationString());

        // list of all Playlists
        tvPlaylists.setItems(playlistListModel.getPlayLists());
        tcName.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getNameProperty());
        tcSongs.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTotalSongsProperty().asObject());
        tcTimeInPlaylist.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getDurationStringProperty());

        //hides the volume slider initially
        hboxVolume.getChildren().remove(sldVolume);

        //hides the Search text box initially
        txtSearch.setVisible(false);

        // Search in all songs
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                songListModel.searchSong(newValue);
            } catch (MyTunesException DALex) {
                displayError(DALex);
            }
        });

        btnVolume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (hboxVolume.lookup("#sldVolume") == null) {
                    sldVolume.setVisible(true);
                    hboxVolume.getChildren().add(sldVolume);
                }
            }
        });

        hboxVolume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hboxVolume.getChildren().remove(sldVolume);
            }
        });
    }

    /** =======================================================================================================
     =============================================== BUTTONS ===============================================
     ======================================================================================================= */

    /**
     * When it is clicked, then turns on the search field and if is clicked again then is disappeared.
     *
     * @param actionEvent runs when a action is performed on SearchButton.
     */
    public void handleSearchButton(ActionEvent actionEvent) {
        toggleSearch();
    }

    private void toggleSearch(){
        if (txtSearch.isVisible()) {
            txtSearch.setText("");
            txtSearch.setVisible(false);
        } else {
            txtSearch.setVisible(true);
            txtSearch.requestFocus();
        }
    }

    /**
     * depending on btnToggleShuffle is selected or not, is calling shuffle or unshuffle method-
     * @param actionEvent runs on action on btnToggleShuffle button.
     */
    public void handleShuffleBtn(ActionEvent actionEvent) {

        if(btnToggleShuffle.isSelected()){
            songPlayer.shufflePlaylist();
        }else{
            songPlayer.unshufflePlaylist();
        }
    }

    /**
     * @param actionEvent runs when an action is performed on the playBtn button
     */
    public void handlePlayBtn(ActionEvent actionEvent) {
        playSong();
    }


    /**
     * plays the song
     * checks if songplayer is null, creates a new songplayer object.
     * checks if the playlist selected is not the same id as songplayer, creates a new songplayer object.
     * otherwise, we wil just resume the song.
     */
    private void playSong() {
        try {
            if (songPlayer == null) {
                songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
            } else if (tvPlaylists.getSelectionModel().getSelectedItem().getIdProperty().get() != songPlayer.getId()) {
                songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
            }
            activateButtons();
            btnPause.setVisible(true);
            btnPlay.setVisible(false);
            songPlayer.play(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex());
            updateProgBar();
            setSongVolume();
        } catch (Exception ex) {
            ex.fillInStackTrace();
            displayMessage("Nothing selected. Please select a playlist and a song.");
        }
    }

    /**
     * activates the buttons.
     */
    private void activateButtons(){
        sldVolume.setDisable(false);
        progressBar.setDisable(false);
        btnToggleShuffle.setDisable(false);
        btnPreviousSong.setDisable(false);
        btnSkipSong.setDisable(false);
    }

    /**
     * @param actionEvent runs when an action is performed on the Pausebtn button.
     */
    public void handlePauseBtn(ActionEvent actionEvent) {
        pauseSong();
    }

    /**
     * pauses a song.
     */
    private void pauseSong() {
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        songPlayer.pause();
        updateProgBar();
    }

    /**
     * switch the scene to NewPlaylistView.fxml.
     *
     * @param actionEvent runs when an action happens on newPlaylistBtn button.
     */
    public void handleNewPlaylistBtn(ActionEvent actionEvent) {
        sceneSwapper.sceneSwitch(new Stage(), "NewPlaylistView.fxml");
    }

    /**
     * switch the scene to a EditPlaylistView.fxml scene.
     * checks for a playlist is selected to be edited.
     * @param actionEvent runs when an action happens on handleEditPlaylistBtn button.
     */
    public void handleEditPlaylistBtn(ActionEvent actionEvent) {
        if (getSelectedPlaylist() == null) {
            displayMessage("There is no playlist select, please select a playlist");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "EditPlaylistView.fxml");
        }
    }

    /**
     * @param actionEvent runs when an action is performed on the handleDeletePlaylistBtn button.
     */
    public void handleDeletePlaylistBtn(ActionEvent actionEvent) {
        deletePlaylist();
    }

    /**
     * deletes a playlist.
     * checks there is a selected playlist.
     * displays a confirmation box.
     */
    private void deletePlaylist() {
        if (getSelectedPlaylist() == null) {
            displayMessage("There is no playlist select, please select a playlist");
        } else {
            if (displayWarning("This action will delete the playlist permanently.")) {
                try {
                    playlistListModel.deletePlaylist(getSelectedPlaylist()); // Ask the model to remove a playlist
                } catch (MyTunesException MyTex) {
                    displayError(MyTex);
                }
            }
        }
    }

    /**
     * @param actionEvent runs when an action is called on the handleAscendSongsInPlaylistBtn button
     */
    public void handleAscendSongInPlaylistBtn(ActionEvent actionEvent) {
        ascendSong();
    }


    /**
     * Ascends a song In the tvSongsOnPlaylist tableview.
     * checks for no selected song from playlist.
     */
    private void ascendSong() {
        if (getSelectedSongFromPlaylist() == null) {
            displayMessage("There is no song select, please select a song");
        } else {
            SongModel songModel = getSelectedSongFromPlaylist();
            PlaylistModel playlistModel = getSelectedPlaylist();
            playlistListModel.AscendSongInPlaylist(playlistModel, songModel);
        }
    }

    /**
     * @param actionEvent runs when action is performed on the handleDescendSongInPlaylistBtn button.
     */
    public void handleDescendSongInPlaylistBtn(ActionEvent actionEvent) {
        descendSong();
    }


    /**
     * descends a song In the tvSongsOnPlaylist tableview.
     * checks for no selected song from playlist.
     */
    private void descendSong() {
        if (getSelectedSongFromPlaylist() == null) {
            displayMessage("There is no song selected, please select a song");
        } else {
            SongModel songModel = getSelectedSongFromPlaylist();
            PlaylistModel playlistModel = getSelectedPlaylist();
            playlistListModel.DescendSongInPlaylist(playlistModel, songModel);
        }
    }

    /**
     * @param actionEvent runs when an action is performed on handleAddSongToPlaylistBtn button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) {
        addSongToPlaylist();
    }


    /**
     * adds a song to a Playlist.
     * checks for a selected song and playlist.
     * and throws an error if the song already exist.
     */
    private void addSongToPlaylist() {
        SongModel songModel = getSelectedSong();
        PlaylistModel playlistModel = getSelectedPlaylist();

        if (getSelectedSong() == null || getSelectedPlaylist() == null) {
            displayMessage("There is no song or playlist selected, please select a song or playlist");
        } else {
            try {
                playlistListModel.addSongToPlaylist(songModel.convertToSong(), playlistModel.convertToPlaylist());
                playlistModel.addSongToPlayList(songModel);
            } catch (MyTunesException MyTex) {
                displayError(new MyTunesException("This song already exist in this playlist."));
            }
        }
    }

    /**
     * @param actionEvent runs when an action is performed on the deleteSongInPlaylistBtn button.
     */
    public void handleDeleteSongInPlaylistBtn(ActionEvent actionEvent) {
        deleteSongFromPlaylist();
    }


    /**
     * deletes a Song From a Playlist.
     * checks for both a song and a playlist.
     * and display a confirmation box.
     */
    private void deleteSongFromPlaylist() {
        if (getSelectedSongFromPlaylist() == null || getSelectedPlaylist() == null) {
            displayMessage("There is no song or playlist selected, please select a song or playlist");
        } else {
            if (displayWarning("This action will delete the song from the playlist.")) {
                try {
                    SongModel songModel = getSelectedSongFromPlaylist();
                    PlaylistModel playlistModel = getSelectedPlaylist();
                    playlistListModel.removeSongFromPlaylist(songModel, playlistModel);
                } catch (MyTunesException MyTex) {
                    displayError(MyTex);
                }
            }
        }
    }

    /**
     * switches the scene over to NewSongView.fxml.
     *
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleNewSongBtn(ActionEvent actionEvent) {
        sceneSwapper.sceneSwitch(new Stage(), "NewSongView.fxml");
    }

    /**
     * switches the scene over to EditSongView.fxml.
     *
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleEditSongBtn(ActionEvent actionEvent) {
        if (getSelectedSong() == null) {
            displayMessage("There is no song selected, please select a song");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "EditSongView.fxml");
        }
    }

    /**
     * @param actionEvent runs when an action is performed on the handleDeleteSongBtn.
     */
    public void handleDeleteSongBtn(ActionEvent actionEvent) throws MyTunesException {
        deleteSong();
    }

    /**
     * Deletes and Songs.
     * Checks for song is selected.
     * and display a confirmation warning.
     */
    private void deleteSong() {
        if (getSelectedSong() == null) {
            displayMessage("There is no song selected, please select a song");
        } else {
            if (displayWarning("This action will delete the song permanently.")) {
                try {
                    songListModel.deleteSong(getSelectedSong());
                } catch (MyTunesException MyTex) {
                    displayError(MyTex);
                }
            }
        }
    }

    /**
     * @param actionEvent when an action is performed on next song button.
     */
    public void handleNextSongBtn(ActionEvent actionEvent) {
        nextSong();
    }

    /**
     * used when wanted to play the next song.
     */
    private void nextSong() {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.nextSong();
        updateSelection();
        updateProgBar();
        setSongVolume();
    }

    /**
     * @param actionEvent will run when an action is called on the previous button.
     */
    public void handlePreviousSongBtn(ActionEvent actionEvent) {
        previousSong();
    }

    /**
     * used when wanted to play previous song.
     */
    private void previousSong() {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.previousSong();
        tvSongsOnPlaylist.getSelectionModel().select(songPlayer.getIndex());
        updateProgBar();
        setSongVolume();
    }

    /** =======================================================================================================
     ============================================ KEYBOARD INPUT ===========================================
     ======================================================================================================= */

    /**
     * Takes input from the keyboard when the MainView (root BorderPane) is in focus.
     *
     * @param keyEvent runs on a keyEvent.
     * @throws MyTunesException
     */
    public void HandleKeyboardInput(KeyEvent keyEvent) throws MyTunesException {
        KeyCode keyCode = keyEvent.getCode(); //A key from the keyboard

        if(keyCode == KeyCode.F && keyEvent.isShortcutDown()){
            toggleSearch();
        } else if (keyCode == KeyCode.ESCAPE && txtSearch.isVisible()){
            toggleSearch();
        }

        switch (keyCode) {
            case F7:
            case SPACE:
            case ENTER:
                if (btnPause.isVisible()) //is the pause button is not visible it means play was pressed last
                    pauseSong();
                else
                    playSong();
                break;
            case F6:
                if (!btnPreviousSong.isDisabled())
                    previousSong();
                break;
            case F8:
                if (!btnSkipSong.isDisabled())
                    nextSong();
                break;
            case PLUS:
                addSongToPlaylist();
                break;
            case BACK_SPACE:
            case DELETE:
                if (tvPlaylists.isFocused())
                    deletePlaylist();
                else if (tvSongsOnPlaylist.isFocused())
                    deleteSongFromPlaylist();
                else if (tvSongs.isFocused())
                    deleteSong();
                break;
        }
    }


    /**
     * =======================================================================================================
     * ============================================== TABLEVIEWS =============================================
     * =======================================================================================================
     */

    /**
     * creates a new playlist.
     * @param playlistName the name of the new playlist.
     * @throws MyTunesException error
     */
    public void infoToNewPlaylist(String playlistName) throws MyTunesException {
        try {
            playlistListModel.createPlaylist(playlistName);
        } catch (MyTunesException MyTex) {
            displayError(MyTex);
        }
    }

    /**
     * Displays the songs of the playlist that is clicked in the tableview of playlists
     */
    public void handleViewSongs(MouseEvent mouseEvent) {
        if (getSelectedPlaylist() != null) {
            tvSongsOnPlaylist.setItems(getSelectedPlaylist().getListOfSongs());
            tcSongsInPlayList.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTitleProperty());
        }
        playlistModel = tvPlaylists.getSelectionModel().getSelectedItem();
    }

    /**
     * @return the selected PlaylistModel on the tvPlaylists Tableview.
     */
    public PlaylistModel getSelectedPlaylist() {
        return tvPlaylists.getSelectionModel().getSelectedItem();
    }


    /**
     * @return the selected SongModel object in the tvSongs Tableview.
     */
    public SongModel getSelectedSong() {
        return tvSongs.getSelectionModel().getSelectedItem();
    }


    /**
     * @return the selected SongModel object in the tvSongsOnPlaylist tableview.
     */
    public SongModel getSelectedSongFromPlaylist() {
        return tvSongsOnPlaylist.getSelectionModel().getSelectedItem();
    }

    /**
     *  clears the selected item on the tvSongs Tableview.
     * @param mouseEvent happens when clicked on the tvSongsOnPlaylist Tableview.
     */
    public void handleTvSongsInPlaylistClicked(MouseEvent mouseEvent) {
        tvSongs.getSelectionModel().clearSelection();
    }


    /**
     *   clears the selected item on the tvSongsOnPlaylist tableview.
     * @param mouseEvent happens when clicked on tvSongs Tableview
     */
    public void handleTvSongClicked(MouseEvent mouseEvent) {
        tvSongsOnPlaylist.getSelectionModel().clearSelection();
    }

    /**
     * updates the selected place on the tableview.
     */
    public void updateSelection() {
            tvSongsOnPlaylist.getSelectionModel().select(songPlayer.getIndex());
    }

    /**
     * creates a song to songlistmodel.
     *
     * @param title the name of the song
     * @param artist the artist of the song
     * @param genre the genre of the song
     * @param duration the duration of the song
     * @param pathToFile the location to the song.
     * @throws MyTunesException error
     */
    public void infoToCreateSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {
        try {
            songListModel.createSong(title, artist, genre, duration, pathToFile);
        } catch (MyTunesException MyTex) {
            displayError(MyTex);
        }
    }

    /**
     * =======================================================================================================
     * ================================================ SLIDERS ==============================================
     * =======================================================================================================
     */

    /**
     * sets the song volume after what the volume sliders value are.
     */
    public void setSongVolume() {
        songPlayer.setVolume(sldVolume.getValue());
    }

    /**
     * gets the music progressbar value
     * @return return the value of the progress bar slider.
     */
    public double getProgBarValue() {
        return progressBar.getValue();
    }

    /**
     *
     * @param event when a drag is entered on progressbar slider.
     */
    public void handleProgBarPressed(MouseEvent event) {
        songPlayer.barDragStart();
    }

    /**
     *
     * @param event when mouse clicked is released from the progressbar slider.
     */
    public void handleProgBarReleased(MouseEvent event) {
        songPlayer.barDragEnd();
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
    }

    /**
     * sets volume og the songs that will be played
     *
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(MouseEvent dragEvent) {
        if (songPlayer != null) {
            setSongVolume();
        }
    }

    /**
     *  here we use a listener changed to move to slider along with the song playing
     *
     *  TODO jan -.-  plus totalingDuration wants to be final.
     */
    public void updateProgBar() {
        songPlayer.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            double totalSongDuration = songPlayer.getSongModel().getDurationProperty().get();

            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
                                Duration newValue) {
                progressBar.setValue((newValue.toSeconds() / songPlayer.getMediaPlayer().getTotalDuration().toSeconds()) * 100);
                lblSongProgress.setText(songPlayer.getSongModel().getDurationString(songPlayer.getMediaPlayer().getCurrentTime().toMillis()));
            }
        });
        lblSongDuration.setText(songPlayer.getSongModel().getDurationString().get());
    }

    /**
     * =======================================================================================================
     * ================================================= LABELS ==============================================
     * =======================================================================================================
     */

    /**
     * updates the label of what song is currently playing.
     *
     * @param title of the song that's currently playing.
     */
    public void updateIsPlayingLabel(String title) {
        lblCurrentSongPlaying.setText(title);
    }

}
