package store.utils.validator;

public class ParserValidator {

    public static void validateString(String input) {
        checkEmpty(input);
    }

    public static void validateNumber(String input) {
        checkEmpty(input);
        checkNonNumeric(input);
    }

    public static void validateDate(String input) {
        checkEmpty(input);
    }

    public static void checkEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 파일의 값이 비어있습니다.");
        }
    }

    public static void checkNonNumeric(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                throw new IllegalArgumentException("[ERROR] 숫자가 아닌 값이 들어있습니다.");
            }
        }
    }
}
