package bll;

import be.MyTunesException;
import dal.db.LogDAO;

import java.io.IOException;
import java.util.Date;

public class LogManager {

    private LogDAO logDAO;

    public LogManager() throws IOException {
        logDAO = new LogDAO();
    }

    public void storeLogInDB(String userName, Date date, String error) throws MyTunesException {
        logDAO.logErrorInDB(userName, date, error);

    }
}
