package be;

public class MyTunesException extends Exception {

    /**
     * different constructors for when we are throwing the exception.
     * depending on if we just want to get the message or also the cause.
     */
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
