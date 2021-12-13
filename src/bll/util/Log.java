package bll.util;

import be.MyTunesException;
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

    public String getUserName() {
        String userName = "MyTunesUser"; // In future it should retrun the login Username for MyTunes App
        return userName;
    }

    public Date getDate() {
        return new Date();
    }

    public void createLog(String error){
        try {
            logManager.storeLogInDB(getUserName(), getDate(), error);
        }catch (MyTunesException MyTex){
            displayError(MyTex);
        }
    }
}
