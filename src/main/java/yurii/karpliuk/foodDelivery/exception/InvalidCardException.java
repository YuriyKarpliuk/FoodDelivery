package yurii.karpliuk.foodDelivery.exception;

public class InvalidCardException extends Exception{
    public InvalidCardException() {
        super("Invalid card number!");
    }
}
