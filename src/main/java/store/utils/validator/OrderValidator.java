package store.utils.validator;

import static store.constants.ErrorMessage.INVALID_FORM;
import static store.constants.ErrorMessage.IS_EMPTY_INPUT;
import static store.constants.OrderConstants.CONSECUTIVE_COMMA;
import static store.constants.OrderConstants.LEFT_SQUARE_BRACKET;
import static store.constants.OrderConstants.PRODUCT_AND_QUANTITY_SIZE;
import static store.constants.OrderConstants.RIGHT_SQUARE_BRACKET;

import java.util.List;

public class OrderValidator {

    public static void validateUserOrder(String userOrderItems) {
        checkInputForm(userOrderItems);
    }

    public static void validateUserOrderForm(List<String> orderItem) {
        checkSize(orderItem);
        checkName(orderItem.getFirst());
        checkQuantity(orderItem.getLast());
    }

    public static void validateCorrectForm(String orderItem) {
        checkCorrectForm(orderItem);
    }

    private static void checkInputForm(String userOrderItem) {
        hasEmptyInput(userOrderItem);
        hasConsecutiveComma(userOrderItem);
    }

    private static void hasEmptyInput(String order) {
        if (order.isBlank()) {
            throw new IllegalArgumentException(IS_EMPTY_INPUT.getMessage());
        }
    }

    private static void hasConsecutiveComma(String userOrderItems) {
        if (userOrderItems.contains(CONSECUTIVE_COMMA)) {
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }

    private static void checkSize(List<String> orderItem) {
        if (orderItem.size() != PRODUCT_AND_QUANTITY_SIZE) {
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }

    private static void checkName(String name) {
        hasEmptyInput(name);
    }

    private static void checkQuantity(String quantity) {
        hasEmptyInput(quantity);
        hasNonNumeric(quantity);
    }

    private static void hasNonNumeric(String quantity) {
        for (int i = 0; i < quantity.length(); i++) {
            if (!Character.isDigit(quantity.charAt(i))) {
                throw new IllegalArgumentException(INVALID_FORM.getMessage());
            }
        }
    }

    private static void checkCorrectForm(String orderItem) {
        if (!(orderItem.startsWith(LEFT_SQUARE_BRACKET) && orderItem.endsWith(RIGHT_SQUARE_BRACKET))) {
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }
}
