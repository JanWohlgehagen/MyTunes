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

    public String getDurationString(){
        int totalTime = getTotalTime();

        int minutes = totalTime  / 60; // divide by 60 to get the minutes from seconds.
        int seconds = totalTime  % 60; // remaining seconds

        return minutes + ":" + seconds;
    }
}
