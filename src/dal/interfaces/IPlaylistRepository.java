package dal.interfaces;

import be.Playlist;
import be.Song;

import java.util.List;

public interface IPlaylistRepository {
    public List<Playlist> getAllPlaylists() throws Exception;

    public void addSongToPLaylist(int songId, int playlistId) throws Exception;

    public List<Song> getSongsFromPlaylist (Playlist playlist) throws Exception;

    public Playlist createPlaylist(String name) throws Exception;

    public void updatePlaylist(Playlist playlist) throws Exception;

    public void deletePlaylist(Playlist playlist) throws Exception;
}
