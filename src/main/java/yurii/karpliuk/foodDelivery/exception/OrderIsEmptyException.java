package yurii.karpliuk.foodDelivery.exception;

public class OrderIsEmptyException extends Exception{
    public OrderIsEmptyException() {
        super("Your order is empty!");
    }
}
