package store.constants.uiConstants;

public enum OutputMessage {
    WELCOME_TO_USER("\n안녕하세요. W편의점입니다."),
    SHOW_CURRENT_STOCK_STATUS("현재 보유하고 있는 상품입니다.\n"),

    CURRENT_PRODUCT("- %s %,d원 %s %s"),
    CURRENT_ZERO_PRODUCT("- %s %,d원 %,d개 %s"),

    START_MESSAGE_FOR_RECEIPT("\n==============W 편의점================"),
    PRODUCT_NAME_QUANTITY_PRICE("상품명                수량        금액"),
    PRINT_FORMAT_FOR_ORDER("%s                %,d        %,d\n"),

    GIFT_QUANTITY("=============증     정==============="),
    FREE_GIFT("%s                %,d\n"),

    DIVIDE_LINE("===================================="),
    TOTAL_PRICE("총구매액               %,d        %,d\n"),
    APPLIED_PROMOTION_PRICE("행사할인                       -%,d\n"),
    MEMBERSHIP_DISCOUNT_PRICE("멤버십할인                      -%,d\n"),
    AMOUNT_DUE("내실돈                          %,d\n")
    ;

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
