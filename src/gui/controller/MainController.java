package gui.controller;

import gui.model.ListModel;
import gui.model.PlaylistModel;
import gui.model.SongModel;
import gui.view.SceneSwapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController  implements Initializable {

    public TableColumn<SongModel, String> tcTitle;
    public TableColumn<SongModel, String> tcArtist;
    public TableColumn<SongModel, String> tcCategory;
    public TableColumn<SongModel, String> tcTime;
    public TableColumn<PlaylistModel, String> txtName;
    public TableColumn<PlaylistModel, Integer>  txtSongs;
    public TableColumn<PlaylistModel, String>  txtTime;
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
    private TextField txtSearch;
    @FXML
    private Slider sldVolume;
    @FXML
    private TableView<PlaylistModel> tvSongsOnPlaylist;
    @FXML
    private TableView<SongModel> tvSongs;
    @FXML
    private TableView<PlaylistModel> tvPlaylists;
    @FXML
    private Label lblCurrentSongPlaying;
    @FXML
    private Button btnPlayPause;


    private final SceneSwapper sceneSwapper;
    private ListModel listModel;
    private PlaylistModel playlistModel;

    public MainController(){
        sceneSwapper = new SceneSwapper();
        listModel = new ListModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // list af alle sange
    tvSongs.setItems(listModel.getSongs());
    tcTitle.setCellValueFactory(addSongToList -> addSongToList.getValue().getTitleProperty());
    tcArtist.setCellValueFactory(addSongToList -> addSongToList.getValue().getArtistProperty());
    tcCategory.setCellValueFactory(addSongToList -> addSongToList.getValue().getGenreProperty());
    tcTime.setCellValueFactory(addSongToList -> addSongToList.getValue().getTimeProperty());

        // list af alle playList
     tvPlaylists.setItems(listModel.getPlayLists());
     txtName.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getNameProperty());
     txtSongs.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getSongsProperty().asObject());
     txtTime.setCellValueFactory(addPlayListToLIst -> addPlayListToLIst.getValue().getTimeProperty());


    txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
        listModel.searchSong(newValue);
    });

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
        System.out.println("hello");
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
        sceneSwapper.sceneSwitch(new Stage(), "NewEditPlaylistView.fxml");
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

