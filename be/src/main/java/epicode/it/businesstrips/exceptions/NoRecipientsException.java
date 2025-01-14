package epicode.it.businesstrips.exceptions;

public class NoRecipientsException extends RuntimeException{
    public NoRecipientsException() {
        super();
    }

    public NoRecipientsException(String message) {
        super(message);
    }
}
