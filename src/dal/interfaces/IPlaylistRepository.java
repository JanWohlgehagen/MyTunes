package dal.interfaces;

import be.Playlist;
import be.Song;

import java.util.List;

public interface IPlaylistRepository {
    public List<Song> getAllPlaylists() throws Exception;

    public void addSongToPLaylist(Song song) throws Exception;

    public List<Song> getSongsFromPlaylist (Playlist playlist) throws Exception;

    public Song createPlaylist(String name) throws Exception;

    public void updatePlaylist(Playlist playlist) throws Exception;

    public void deletePlaylist(Playlist playlist) throws Exception;
}
