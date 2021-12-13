package be;

public class Song {

    private String title;
    private String artist;
    private String genre;
    private double duration;
    private int id;
    private String pathToFile;

    /**
     *  constructor for song used for creating an instance of a Song.
     * @param id the int id of the song
     * @param title the title of the song
     * @param artist the artist of the song
     * @param genre the genre of the song
     * @param duration the duration of the song
     * @param pathToFile the location of the song
     */
    public Song(int id, String title, String artist, String genre, double duration, String pathToFile) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.pathToFile = pathToFile;
    }

    /**
     *  used for getting an id of the song.
     * @return the id og song
     */
    public int getId() {
        return id;
    }

    /**
     * ysed for getting the title of the song
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * used for getting the artist of the song
     * @return the Artist of a song
     */
    public String getArtist() {
        return artist;
    }

    /**
     *  used for getting the genre of the song.
     * @return the genre of the song
     */
    public String getGenre() {
        return genre;
    }

    /**
     *  used for getting the duration of the song
     * @return the duration of the song in double.
     */
    public double getDuration() {
        return duration;
    }

    /**
     * used for getting the location of the song.
     * @return the path to file used. as a string
     */
    public String getPathToFile() {
        return pathToFile;
    }
}
