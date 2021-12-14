package bll;

import be.Playlist;
import be.Song;
import be.MyTunesException;
import dal.db.PlaylistDAO;
import dal.interfaces.IPlaylistRepository;
import java.io.IOException;
import java.util.List;

import static be.DisplayMessage.displayError;

public class PlaylistManager {

    private IPlaylistRepository playListDAO;

    public PlaylistManager()  {
        try {
            playListDAO = new PlaylistDAO();
        } catch (IOException e) {
            displayError(new MyTunesException(playListDAO.ERROR_STRING, e.fillInStackTrace()));
        }
    }

    /**
     * Returns all playlists from the DB
     * @return
     * @throws MyTunesException
     */
    public List<Playlist> getAllPlaylists() throws MyTunesException {
       return playListDAO.getAllPlaylists();
    }

    /**
     * Adds a song to a playlist in the relation table in the DB
     * @param song
     * @param playlist
     * @throws MyTunesException
     */

    public void addSongToPLaylist(Song song, Playlist playlist) throws MyTunesException {
        playListDAO.addSongToPLaylist(song, playlist);
    }

    /**
     * Remove a song form is playlist, in the DB
     *
     * @param song
     * @param playlist
     * @throws MyTunesException
     */

    public void removeSongFromPLaylist(Song song, Playlist playlist) throws MyTunesException {
        playListDAO.removeSongFromPlaylist(song, playlist);
    }

    /**
     * Creates the playlist in DB
     * @param name
     * @return
     * @throws MyTunesException
     */
    public Playlist createPlaylist(String name) throws MyTunesException {
        return playListDAO.createPlaylist(name);
    }

    /**
     * Updates the playlist in the DB
     * @param playlist
     * @throws MyTunesException
     */
    public void updatePlaylist(Playlist playlist) throws MyTunesException {
        playListDAO.updatePlaylist(playlist);
    }

    /**
     * Remove the playlist from DB
     * @param playlist
     * @throws MyTunesException
     */
    public void deletePlaylist(Playlist playlist) throws MyTunesException {
        playListDAO.deletePlaylist(playlist);
    }
}
