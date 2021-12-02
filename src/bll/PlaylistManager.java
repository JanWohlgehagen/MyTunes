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

    public List<Playlist> getAllPlaylists() throws DALException{
       return playListDAO.getAllPlaylists();
    }

    public void addSongToPLaylist(int songId, int playlistId) throws DALException{
        playListDAO.addSongToPLaylist(songId, playlistId);
    }

    public List<Song> getSongsFromPlaylist (int playlistId) throws DALException{
        return playListDAO.getSongsFromPlaylist(playlistId);
    }

    public Playlist createPlaylist(String name) throws DALException{
        return playListDAO.createPlaylist(name);
    }

    public void updatePlaylist(Playlist playlist) throws DALException{
        playListDAO.updatePlaylist(playlist);
    }

    public void deletePlaylist(Playlist playlist) throws DALException{
        playListDAO.deletePlaylist(playlist);
    }
}
