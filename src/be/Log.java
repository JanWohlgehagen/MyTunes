package be;

import bll.LogManager;
import java.util.Date;

import static be.DisplayMessage.displayError;

public class Log {
    private LogManager logManager;


    public Log() {
        try {
            logManager = new LogManager();
        }catch (Exception ex){
            displayError(ex);
        }
    }

    /**
     * Get the name of the user
     *
     * @return
     */
    public String getUserName() {
        String userName = "MyTunesUser"; // In future it should retrun the login Username for MyTunes App
        return userName;
    }

    /**
     * Set the date for when an error happens.
     * @return
     */

    public Date setDate() {
        return new Date();
    }

    /**
     * Create a log of the error, that occurred
     *
     * @param error
     */
    public void createLog(String error){
        try {
            logManager.storeLogInDB(getUserName(), setDate(), error);
        }catch (MyTunesException MyTex){
            displayError(MyTex);
        }
    }
}
