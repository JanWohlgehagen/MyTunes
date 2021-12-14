package dal.db;

import be.Playlist;
import be.Song;
import be.MyTunesException;
import dal.interfaces.IPlaylistRepository;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistRepository {

    private MyConnection databaseConnector;

    public PlaylistDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    /**
     *
     * Get all the playlist in DB
     *
     * @return
     * @throws MyTunesException
     */

    @Override
    public List<Playlist> getAllPlaylists() throws MyTunesException {
        List<Playlist> allPlaylists = new ArrayList<>();
        List<Song> allsongs = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Playlist;";
            String sql1 = "SELECT * FROM Song FULL JOIN PlaylistSongs ON Song.id = songId WHERE  PlaylistSongs.playlistId = (?);"; //1. sql command
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1); //Create statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    allsongs.clear();
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("Name");

                    preparedStatement1.setInt(1,id);
                    if(preparedStatement1.execute()){
                        ResultSet resultSet1 = preparedStatement1.getResultSet();
                        while(resultSet1.next()) {
                            int idSong = resultSet1.getInt("id");
                            String title = resultSet1.getString("title");
                            String artist = resultSet1.getString("artist");
                            String genre = resultSet1.getString("genre");
                            int duration = resultSet1.getInt("duration");
                            String pathToFile = resultSet1.getString("filePath");
                            allsongs.add(new Song(idSong, title, artist, genre, duration, pathToFile));
                        }
                    }
                        Playlist playlist = new Playlist(id, name);
                        playlist.addSongToPlayList(allsongs);
                    allPlaylists.add(playlist);
                }
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
        return allPlaylists;
    }

    /**
     * Get all songs form a playlist in DB
     *
     * @param playlistId
     * @return
     * @throws MyTunesException
     */

    @Override
    public List<Song> getSongsFromPlaylist(int playlistId) throws MyTunesException {
        List<Song> songsInPlaylist = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT id, title, filePath, artist, genre, duration FROM Song INNER JOIN PlaylistSongs ON PlaylistSongs.songId = Song.id WHERE playlistId =(?);"; //sql command
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, playlistId);

            //Extract data from DB
            if(preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String genre = resultSet.getString("genre");
                    int duration = resultSet.getInt("duration");
                    String pathToFile = resultSet.getString("filePath");

                    songsInPlaylist.add(new Song(id, title, artist, genre, duration, pathToFile));
                }
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
        return songsInPlaylist;
    }

    /**
     * Add a song form playlist in DB.
     *
     * @param song
     * @param playlist
     * @throws MyTunesException
     */

    @Override
    public void addSongToPLaylist(Song song, Playlist playlist) throws MyTunesException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO PlaylistSongs VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }

    /**
     * Delete a song form playlist in DB
     *
     * @param song
     * @param playlist
     * @throws MyTunesException
     */
    @Override
    public void removeSongFromPlaylist(Song song, Playlist playlist) throws MyTunesException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM PlaylistSongs WHERE songId = (?) AND playlistId = (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, song.getId());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }

    /**
     * Create a playlist in DB
     *
     * @param name
     * @return
     * @throws MyTunesException
     */

    @Override
    public Playlist createPlaylist(String name) throws MyTunesException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Playlist VALUES (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);

                    return new Playlist(id, name);
                }
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
        return null;
    }

    /**
     * Update a playlist in DB
     *
     * @param playlist
     * @throws MyTunesException
     */

    @Override
    public void updatePlaylist(Playlist playlist) throws MyTunesException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "UPDATE Playlist SET name=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new MyTunesException(ERROR_STRING);
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }

    /**
     * Delete a playlist in DB
     *
     * @param playlist
     * @throws MyTunesException
     */

    @Override
    public void deletePlaylist(Playlist playlist) throws MyTunesException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Playlist WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new MyTunesException(ERROR_STRING);
            }
        } catch (SQLException SQLex) {
            throw new MyTunesException(ERROR_STRING, SQLex.fillInStackTrace());
        }
    }
}

