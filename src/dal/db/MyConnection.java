package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class MyConnection {
    private static final String PROP_FILE = "data/database.settings";
    private SQLServerDataSource ds;

    public MyConnection() throws IOException {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(new File(PROP_FILE)));

        String server = databaseProperties.getProperty("Server");
        String database = databaseProperties.getProperty("Database");
        String user = databaseProperties.getProperty("User");
        String password = databaseProperties.getProperty("Password");

        ds = new SQLServerDataSource();
        ds.setServerName(server);
        ds.setDatabaseName(database);
        ds.setUser(user);
        ds.setPassword(password);
    }

    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }

    /**
    public static void main(String[] args) throws IOException, SQLException {
        MyConnection myConnection = new MyConnection();

        Connection connection = myConnection.getConnection();

        System.out.println("Return false if the connection is open. Return true if there is no connection to the database.");
        System.out.print(connection.isClosed());
    }
     */
}
