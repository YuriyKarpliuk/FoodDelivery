package yurii.karpliuk.foodDelivery.exception;

public class NotFoundException extends RuntimeException{
    private static final String NOT_FOUND = " does not found";

    public NotFoundException(String message) {
        super(message + NOT_FOUND);
    }
}
