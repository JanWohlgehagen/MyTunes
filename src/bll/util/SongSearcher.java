package bll.util;

import be.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher implements ISearcher {

    /**
     * Searches through song list, to find a song that matches the key word
     *
     * @param searchBase the list of song it should search through
     * @param query the search key word
     * @return a list of songs that fit, the key word
     */
    @Override
    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song : searchBase) {
            if(compareToSongTitle(song, query) || compareToSongArtist(song, query) || compareToSongGerne(song, query))
            {
                searchResult.add(song);
            }
        }

        return searchResult;
    }

    @Override
    public boolean compareToSongTitle(Song song, String query) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public boolean compareToSongArtist(Song song, String query) {
        return song.getArtist().toLowerCase().contains(query.toLowerCase());
    }

    @Override
    public boolean compareToSongGerne(Song song, String query) {
        return song.getGenre().toLowerCase().contains(query.toLowerCase());
    }
}
