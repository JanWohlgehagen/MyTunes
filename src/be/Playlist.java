package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;
    private List<Song> songList;

    public Playlist(String name){
        this.name = name;
        this.songList = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void addSongToPlayList(Song song){
        this.songList.add(song);
    }

    public int getTotalTime(){
        int totalTime = 0;
        for (Song song: getSongList()) {
            totalTime += song.getDuration();
        }
        return totalTime;
    }
}
