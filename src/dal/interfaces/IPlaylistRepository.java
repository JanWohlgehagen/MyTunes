package dal.interfaces;

import be.Playlist;
import be.Song;
import be.MyTunesException;
import java.util.List;

public interface IPlaylistRepository {

    String ERROR_STRING = "Error: Cannot access database.";

    public List<Playlist> getAllPlaylists() throws MyTunesException;

    public void addSongToPLaylist(Song song, Playlist playlist) throws MyTunesException;

    public void removeSongFromPlaylist(Song song, Playlist playlist) throws MyTunesException;

    public List<Song> getSongsFromPlaylist (int playlistId) throws MyTunesException;

    public Playlist createPlaylist(String name) throws MyTunesException;

    public void updatePlaylist(Playlist playlist) throws MyTunesException;

    public void deletePlaylist(Playlist playlist) throws MyTunesException;
}
