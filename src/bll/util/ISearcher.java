package bll.util;

import be.Song;

import java.util.List;

public interface ISearcher {

    public List<Song> search(List<Song> searchBase, String query);

    public boolean compareToSongTitle(Song song, String query);

    public boolean compareToSongArtist(Song song, String query);
}
