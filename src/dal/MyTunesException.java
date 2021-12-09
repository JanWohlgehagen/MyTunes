package dal;

public class MyTunesException extends Exception {

    public MyTunesException()
    {
        super();
    }

    public MyTunesException(String message)
    {
        super(message);
    }

    public MyTunesException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MyTunesException(String message, Exception ex)
    {
        super(message, ex);
    }
}
