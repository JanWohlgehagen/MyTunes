package dal.db;

import be.Song;
import dal.DALException;
import dal.interfaces.ISongRepository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongRepository {

    private MyConnection databaseConnector;

    public SongDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    @Override
    public List<Song> getAllSongs() throws DALException {
        List<Song> allSongsList = new ArrayList<>();

        //Create a connection
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Song;"; //sql command
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

                    allSongsList.add(new Song(id, title, artist, genre, duration, pathToFile));
                }
            }
        }  catch (SQLException SQLex) {
           throw new DALException("Error: Can not 'getAllSongs' in Databases", SQLex.getCause());
        }
        return allSongsList;
    }

    @Override
    public Song createSong(String title, String artist, String genre, int duration, String pathToFile) throws DALException {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Song VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, pathToFile);
            preparedStatement.setString(3, artist);
            preparedStatement.setString(4, genre);
            preparedStatement.setInt(5, duration);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);

                    return new Song(id, title, artist, genre, duration, pathToFile);
                }
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'createSong' in Databases", SQLex.getCause());
        }
        return null;
    }

    @Override
    public void updateSong(Song song) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "UPDATE Song SET title = ?, filePath=?, artist=?, genre=?, duration=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getPathToFile());
            preparedStatement.setString(3, song.getArtist());
            preparedStatement.setString(4, song.getGenre());
            preparedStatement.setInt(5, song.getDuration());
            preparedStatement.setInt(6, song.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException();
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'updateSong' in Databases", SQLex.getCause());
        }
    }

    @Override
    public void deleteSong(Song song) throws DALException {
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "DELETE FROM Song WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows != 1){
                throw new DALException();
            }
        } catch (SQLException SQLex) {
            throw new DALException("Error: Can not 'deleteSong' in Databases", SQLex.getCause());
        }
    }
}


