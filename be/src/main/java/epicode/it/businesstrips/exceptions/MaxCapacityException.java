package epicode.it.businesstrips.exceptions;

public class MaxCapacityException extends RuntimeException{
    public MaxCapacityException() {
        super();
    }

    public MaxCapacityException(String message) {
        super(message);
    }
}
