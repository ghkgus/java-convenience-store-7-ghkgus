package store.utils.validator;

import static store.constants.ErrorMessage.INVALID_FORM;

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
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }

    private static void hasConsecutiveComma(String userOrderItems) {
        if (userOrderItems.contains(",,")) {
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }

    private static void checkSize(List<String> orderItem) {
        if (orderItem.size() != 2) {
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

    private static void checkCorrectForm(String beforeCheckOrderItem) {
        if (!(beforeCheckOrderItem.startsWith("[") && beforeCheckOrderItem.endsWith("]"))) {
            throw new IllegalArgumentException(INVALID_FORM.getMessage());
        }
    }
}
