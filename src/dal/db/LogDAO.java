package dal.db;

import be.MyTunesException;
import be.Song;

import java.io.IOException;
import java.sql.*;
import java.util.Date;


public class LogDAO {

    private MyConnection databaseConnector;

    public LogDAO() throws IOException {
        databaseConnector = new MyConnection();
    }

    /**
     * Log the error in DB
     *
     * @param userName
     * @param date
     * @param error
     * @throws MyTunesException
     */

    public void logErrorInDB(String userName, Date date, String error) throws MyTunesException {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO ErrorLog VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userName);
            preparedStatement.setObject(2, date);
            preparedStatement.setString(3, error);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows != 1) {
                throw new MyTunesException(affectedRows + " affectedRows  in Log(DAL)");
            }

        } catch (SQLException SQLex) {
            throw new MyTunesException("Error: Cannot access database.", SQLex.fillInStackTrace());
        }
    }
}
