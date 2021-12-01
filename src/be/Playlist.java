package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String name;
    private int id;
    private List<Song> songList;

    public Playlist(int id, String name){
        this.name = name;
        this.id = id;
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

    public int getId(){
        return id;
    }

    public int getTotalTime(){
        int totalTime = 0;
        for (Song song: getSongList()) {
            totalTime += song.getDuration();
        }
        return totalTime;
    }
}
