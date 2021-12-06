package gui.model;

import be.Song;
import bll.PlaylistManager;
import dal.DALException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.List;


public class PlayListSongModel {

    private StringProperty title = new SimpleStringProperty();

    public PlayListSongModel(String title) throws IOException {
        this.getTitleProperty().set(title);
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    PlaylistManager playlistManager = new PlaylistManager();

    public List<Song> getAllSongsInPlaylist(int playlistID) throws DALException {
        return playlistManager.getSongsFromPlaylist(playlistID);
    }
    public Song playCurrentSong(String songName, int playlistID) throws DALException {
        List<Song> songs = getAllSongsInPlaylist(playlistID);
        for (Song song : songs) {
            if (songName.contains(song.getTitle())) {
                return song;
            }
        }
        return null;
    }

    public Song skipSong(Song currentSong, int playlistID) throws DALException {

        List<Song> songs = getAllSongsInPlaylist(playlistID);
        boolean foundSong = false;
        for (Song song : songs){
            if(foundSong){
                return song;
            }
            else if(song.getPathToFile().equals(currentSong.getPathToFile())){
                foundSong = true;
            }
        }
        return null;
    }

    public Song previousSong(Song currentSong, int playlistID) throws DALException {

        List<Song> songs = getAllSongsInPlaylist(playlistID);
        Song previousSong = null;
        for (Song song : songs){
            if(song.getPathToFile().equals(currentSong.getPathToFile())){
                return previousSong;
            }
            previousSong = song;
        }
        return null;
    }
}
