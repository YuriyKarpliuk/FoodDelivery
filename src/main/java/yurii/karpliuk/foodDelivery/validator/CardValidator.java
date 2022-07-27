package yurii.karpliuk.foodDelivery.validator;

import yurii.karpliuk.foodDelivery.exception.InvalidCardException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {
    private static final String CARD_PATTERN =
            "^4[0-9]{12}(?:[0-9]{3})?$";

    private static final Pattern pattern = Pattern.compile(CARD_PATTERN);

    public static boolean isValid(final String card) throws InvalidCardException {
        Matcher matcher = pattern.matcher(card);
        boolean result = matcher.matches();
        if (result)
            return true;
        else throw new InvalidCardException();
    }
}
