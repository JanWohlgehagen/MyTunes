package bll;

import be.Playlist;
import be.Song;
import dal.DALException;
import dal.db.PlaylistDAO;
import dal.interfaces.IPlaylistRepository;

import java.io.IOException;
import java.util.List;

public class PlaylistManager {

    private IPlaylistRepository playListDAO;

    public PlaylistManager() throws IOException {
        playListDAO = new PlaylistDAO();
    }

    /**
     * Returns all playlists from the DB
     * @return
     * @throws DALException
     */
    public List<Playlist> getAllPlaylists() throws DALException{
       return playListDAO.getAllPlaylists();
    }

    /**
     * Adds a song to a playlist in the relation table in the DB
     * @param songId
     * @param playlistId
     * @throws DALException
     */
    public void addSongToPLaylist(int songId, int playlistId) throws DALException{
        playListDAO.addSongToPLaylist(songId, playlistId);
    }

    public void removeSongFromPLaylist(int songId, int playlistId) throws DALException{
        playListDAO.removeSongFromPlaylist(songId, playlistId);
    }

    /**
     * Returns all songs in a given playlist from the DB
     * @param playlistId
     * @return
     * @throws DALException
     */
    public List<Song> getSongsFromPlaylist (int playlistId) throws DALException{
        return playListDAO.getSongsFromPlaylist(playlistId);
    }

    /**
     * Creates the playlist in DB
     * @param name
     * @return
     * @throws DALException
     */
    public Playlist createPlaylist(String name) throws DALException{
        return playListDAO.createPlaylist(name);
    }

    /**
     * Updates the playlist in the DB
     * @param playlist
     * @throws DALException
     */
    public void updatePlaylist(Playlist playlist) throws DALException{
        playListDAO.updatePlaylist(playlist);
    }

    /**
     * Remove the playlist from DB
     * @param playlist
     * @throws DALException
     */
    public void deletePlaylist(Playlist playlist) throws DALException{
        playListDAO.deletePlaylist(playlist);
    }
}
