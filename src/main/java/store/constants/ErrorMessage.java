package store.constants;

public enum ErrorMessage {
    IS_EMPTY_INPUT("빈문자열이 입력되었습니다. 다시 입력해주세요."),
    INVALID_FORM("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    HAVE_TO_ONLY_NUM("숫자만 입력가능합니다. 다시 입력해주세요"),

    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    HAVE_TO_INTEGER_RANGE("정수 범위의 수를 입력해야 합니다. 다시 입력해주세요"),
    HAVE_TO_OVER_ONE("하나 이상의 상품 수량을 입력해야 합니다. 다시 입력해주세요"),
    OVER_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_ANSWER_TYPE("Y, N 이외의 값을 입력하실 수 없습니다. 다시 입력해주세요."),
    ;
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
