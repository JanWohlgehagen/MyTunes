package dal.db;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DALException;
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

    @Override
    public List<Playlist> getAllPlaylists() throws DALException {
        List<Playlist> allPlaylists = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Playlist;"; //sql command
            Statement statement = connection.createStatement(); //Create statement

            //Extract data from DB
            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("Name");


                    Playlist playlist = new Playlist(id, name);
                    allPlaylists.add(playlist);
                }
            }
        } catch (SQLException SQLex) {
           throw new DALException("Error");
        }
        return allPlaylists;
    }

    @Override
    public List<Song> getSongsFromPlaylist(Playlist playlist) throws DALException {
        List<Song> songsInPlaylist = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT id, title, filePath, artist, genre, duration FROM Song INNER JOIN PlaylistSongs ON PlaylistSongs.songId = Song.id;"; //sql command
            Statement statement = connection.createStatement(); //Create statement

            //Extract data from DB
            if(statement.execute(sql)){
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    String genre = resultSet.getString("genre");
                    int duration = resultSet.getInt("duration");
                    String pathToFile = resultSet.getString("filePath");

                    Song song = new Song(id, title, artist, genre, duration, pathToFile);
                    songsInPlaylist.add(song);
                }
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
        return songsInPlaylist;
    }

    @Override
    public void addSongToPLaylist(int songId, int playlistId) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO PlaylistSongs VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, songId);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.executeUpdate();

        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }


    @Override
    public Playlist createPlaylist(String name) throws DALException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Playlist VALUES (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    Playlist playlist = new Playlist(id, name);
                    return playlist;
                }
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
        return null;
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "UPDATE Playlist SET name=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException("Error");
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Playlist WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException("Error");
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error");
        }
    }

}

