package gui.model;

import bll.SongPlayer;

public class SongPlayerModel {

    private SongPlayer songPlayer;

    public SongPlayerModel(String songUrl){
        songPlayer = new SongPlayer(songUrl);
    }
    public void playSong(){songPlayer.playSong();}

    public void pauseSong(){songPlayer.pauseMusic();}

    public Double getSongVolume(){return songPlayer.getVolume();}

    public void setSongVolume(double volume){songPlayer.setVolume(volume);}


}
