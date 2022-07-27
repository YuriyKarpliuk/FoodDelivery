package yurii.karpliuk.foodDelivery.exception;

public class DishNotFoundException extends RuntimeException{
    public DishNotFoundException(String message) {
        super(message);
    }

}
