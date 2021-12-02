package bll;

public class BLLException extends Exception{

    public BLLException()
    {
        super();
    }

    public BLLException(String message)
    {
        super(message);
    }

    public BLLException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BLLException(String message, Exception ex)
    {
        super(message, ex);
    }
}
