package co.ingrow.android.exception;



public abstract class InGrowException extends RuntimeException {

    public InGrowException() {
        super();
    }

    public InGrowException(Throwable cause) {
        super(cause);
    }

    public InGrowException(String message) {
        super(message);
    }

    public InGrowException(String message, Throwable cause) {
        super(message, cause);
    }
}
