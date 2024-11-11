package store.view;

import static store.constants.InputMessage.*;
import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public String getUserOrder() {
        System.out.println(GET_USER_ORDER_MESSAGE.getMessage());
        return Console.readLine();
    }

    public String askUserForRegularPricePayment(String name, int nonGiftQuantity) {
        System.out.printf(ASK_USER_FOR_PAY_REGULAR_PRICE.getMessage(), name, nonGiftQuantity);
        return Console.readLine();
    }

    public String askUserForExtraGift(String name) {
        System.out.printf(ASK_USER_FOR_EXTRA_FREE.getMessage(), name);
        return Console.readLine();
    }

    public String askUserToApplyMembership() {
        System.out.println();
        System.out.println(ASK_USER_FOR_MEMBERSHIP_DISCOUNT.getMessage());
        return Console.readLine();
    }

    public String askUserToRepurchase() {
        System.out.println();
        System.out.println(ASK_USER_FOR_REPURCHASE.getMessage());
        return Console.readLine();
    }
}
