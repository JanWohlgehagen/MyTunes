package gui.controller;

import be.MyTunesException;
import gui.model.PlaylistListModel;
import gui.model.PlaylistModel;
import gui.model.SongListModel;
import gui.model.SongModel;
import gui.util.SceneSwapper;
import gui.util.SongPlayer;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    private Slider progressBar;

    private SceneSwapper sceneSwapper;
    private SongPlayer songPlayer;
    private PlaylistModel playlistModel;
    private SongListModel songListModel;
    private PlaylistListModel playlistListModel;


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvSongsOnPlaylist.setPlaceholder(new Label("Select a playlist \n with songs"));

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
            } catch (MyTunesException DALex) {
                displayError(DALex);
            }
        });

        progressBar.styleProperty().bind(Bindings.createStringBinding(() -> {
            double percentage = (progressBar.getValue() - progressBar.getMin()) / (progressBar.getMax() - progressBar.getMin()) * 100.0 ;
            return String.format("-slider-track-color: linear-gradient(to right, -slider-filled-track-color 0%%, "
                            + "-slider-filled-track-color %f%%, -fx-base %f%%, -fx-base 100%%);",
                    percentage, percentage);
        }, progressBar.valueProperty(), progressBar.minProperty(), progressBar.maxProperty()));
        
        sldVolume.styleProperty().bind(Bindings.createStringBinding(() -> {
            double percentage = (sldVolume.getValue() - sldVolume.getMin()) / (sldVolume.getMax() - sldVolume.getMin()) * 100.0 ;
            return String.format("-slider-track-color: linear-gradient(to right, -slider-filled-track-color 0%%, "
                            + "-slider-filled-track-color %f%%, -fx-base %f%%, -fx-base 100%%);",
                    percentage, percentage);
        }, sldVolume.valueProperty(), sldVolume.minProperty(), sldVolume.maxProperty()));
    }


    /**
     * switch the scene to a different scene.
     *
     * @param actionEvent runs when an action happens on a button.
     */
    public void handleNewPlaylistBtn(ActionEvent actionEvent) {
        sceneSwapper.sceneSwitch(new Stage(), "NewPlaylistView.fxml");
    }


    public void infoToNewPlaylist(String playlistName) throws MyTunesException{
        try {
            playlistListModel.createPlaylist(playlistName);
        }catch (MyTunesException MyTex){
            displayError(MyTex);
        }
    }

    /**
     * switch the scene to a EditPlaylistView.fxml scene.
     *
     * @param actionEvent runs when an action happens on a button.
     */

    public void handleEditPlaylistBtn(ActionEvent actionEvent){
        if (getSelectedPlaylist() == null) {
            displayMessage("There is no playlist select, please select a playlist");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "EditPlaylistView.fxml");
        }
    }

    /**
     * deletes a playlist from the application
     *
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleDeletePlaylistBtn(ActionEvent actionEvent) {
        if (getSelectedPlaylist() == null) {
            displayMessage("There is no playlist select, please select a playlist");
        } else {
            if (displayWarning("This action will delete the playlist permanently.")) {
                try {
                    playlistListModel.deletePlaylist(getSelectedPlaylist()); // Ask the model to remove a playlist
                }catch (MyTunesException MyTex){
                    displayError(MyTex);
                }
            }
        }
    }

    /**
     * moves a song up by one in the tableview.
     *
     * @param actionEvent runs when an action is called on the button
     */
    public void handleAscendSongInPlaylistBtn(ActionEvent actionEvent) {
        if (getSelectedSongFromPlaylist() == null) {
            displayMessage("There is no song select, please select a song");
        } else {
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
        if (getSelectedSongFromPlaylist() == null) {
            displayMessage("There is no song select, please select a song");
        } else {
            SongModel songModel = getSelectedSongFromPlaylist();
            PlaylistModel playlistModel = getSelectedPlaylist();
            playlistListModel.DescendSongInPlaylist(playlistModel, songModel);
        }
    }

    /**
     * adds a song to a certain playlist
     *
     * @param actionEvent runs when an action is performed on a button.
     */
    public void handleAddSongToPlaylistBtn(ActionEvent actionEvent) {
        SongModel songModel = getSelectedSong();
        PlaylistModel playlistModel = getSelectedPlaylist();

        if (getSelectedSong() == null || getSelectedPlaylist() == null) {
            displayMessage("There is no song or playlist select, please select a song or playlist");
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
     * deletes a song from a playlist.
     *
     * @param actionEvent runs when an action is performed on the button.
     */
    public void handleDeleteSongInPlaylistBtn(ActionEvent actionEvent) {
        if (getSelectedSongFromPlaylist() == null || getSelectedPlaylist() == null) {
            displayMessage("There is no song or playlist select, please select a song or playlist");
        } else {
            if (displayWarning("This action will delete the song from the playlist.")) {
                try {
                    SongModel songModel = getSelectedSongFromPlaylist();
                    PlaylistModel playlistModel = getSelectedPlaylist();
                    playlistListModel.removeSongFromPlaylist(songModel, playlistModel);
                }catch (MyTunesException MyTex){
                    displayError(MyTex);
                }
            }
        }
    }

    public void handleViewSongs(MouseEvent mouseEvent) {
        if (getSelectedPlaylist() != null) {
            tvSongsOnPlaylist.setItems(getSelectedPlaylist().getListOfSongs());
            txtSongsInPlayList.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTitleProperty());
        }
        playlistModel = tvPlaylists.getSelectionModel().getSelectedItem();
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
            displayMessage("There is no song select, please select a song");
        } else {
            sceneSwapper.sceneSwitch(new Stage(), "EditSongView.fxml");
        }
    }

    /**
     * removes a song
     *
     * @param actionEvent runs when an action is performed.
     */
    public void handleDeleteSongBtn(ActionEvent actionEvent) throws MyTunesException {
        if (getSelectedSong() == null) {
            displayMessage("There is no song select, please select a song");
        } else {
            if (displayWarning("This action will delete the song permanently.")) {
                try {
                    songListModel.deleteSong(getSelectedSong());
                }catch (MyTunesException MyTex){
                    displayError(MyTex);
                }
            }
        }
    }

    public PlaylistModel getSelectedPlaylist() {
        return tvPlaylists.getSelectionModel().getSelectedItem();
    }

    public SongModel getSelectedSong() {
        return tvSongs.getSelectionModel().getSelectedItem();
    }

    public SongModel getSelectedSongFromPlaylist() {
        return tvSongsOnPlaylist.getSelectionModel().getSelectedItem();
    }

    public void infoToCreateSong(String title, String artist, String genre, double duration, String pathToFile) throws MyTunesException {
        try {
            songListModel.createSong(title, artist, genre, duration, pathToFile);
        }catch (MyTunesException MyTex) {
            displayError(MyTex);
        }
    }

//____________________________________mediaPlayer___________________________________________

    /**
     * sets volume og the songs that will be played
     *
     * @param dragEvent when you move the slider you change the volume
     */
    public void sldVolumeInput(MouseEvent dragEvent) {
        if (songPlayer != null) {
            songPlayer.setVolume(sldVolume.getValue());
        }
    }

    public void handleTvSongsInPlaylistClicked(MouseEvent mouseEvent) {
        tvSongs.getSelectionModel().clearSelection();
    }

    public void handleTvSongClicked(MouseEvent mouseEvent) {
        tvSongsOnPlaylist.getSelectionModel().clearSelection();
    }

    public double getProgBarValue() {
        return progressBar.getValue();
    }

    public void handleProgBarPressed(MouseEvent event) {
        songPlayer.barDragStart();
    }

    public void handleProgBarReleased(MouseEvent event) {
        songPlayer.barDragEnd();
    }

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

    public void updateIsPlayingLabel(String title) {
        lblCurrentSongPlaying.setText(title);
    }

    /**
     * skips current song to next song
     *
     * @param actionEvent when an action is performed on button program will run
     */
    public void handleNextSongBtn(ActionEvent actionEvent) {
        btnPause.setVisible(true);
        btnPlay.setVisible(false);
        songPlayer.nextSong();
        updateSelection();
        updateProgBar();
    }

    public void updateSelection() {
        tvSongsOnPlaylist.getSelectionModel().select(songPlayer.getIndex());
    }

    /**
     * goes back to previous song
     *
     * @param actionEvent will run when an action is called on the button
     */
    public void handlePreviousSongBtn(ActionEvent actionEvent){
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
    public void handlePlayBtn(ActionEvent actionEvent) {
        try {
            if (songPlayer == null) {
                songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
            } else if (tvPlaylists.getSelectionModel().getSelectedItem().getIdProperty().get() != songPlayer.getId()) {
                songPlayer = new SongPlayer(tvSongsOnPlaylist.getItems(), playlistModel.getIdProperty().get());
            }
            btnPreviousSong.setDisable(false);
            btnSkipSong.setDisable(false);
            btnPause.setVisible(true);
            btnPlay.setVisible(false);
            sldVolume.setDisable(false);
            progressBar.setDisable(false);
            songPlayer.play(tvSongsOnPlaylist.getSelectionModel().getSelectedIndex());
            updateProgBar();
            songPlayer.setVolume(sldVolume.getValue());
        } catch (Exception ex) {
            displayMessage("Nothing selected. Please select a playlist and a song.");
        }
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
