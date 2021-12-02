package dal.interfaces;

import be.Playlist;
import be.Song;
import dal.DALException;

import java.util.List;

public interface IPlaylistRepository {
    public List<Playlist> getAllPlaylists() throws DALException;

    public void addSongToPLaylist(int songId, int playlistId) throws DALException;

    public List<Song> getSongsFromPlaylist (Playlist playlist) throws DALException;

    public Playlist createPlaylist(String name) throws DALException;

    public void updatePlaylist(Playlist playlist) throws DALException;

    public void deletePlaylist(Playlist playlist) throws DALException;
}
