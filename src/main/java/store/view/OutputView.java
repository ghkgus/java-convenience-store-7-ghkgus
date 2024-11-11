package store.view;

import static store.constants.ProductConstants.PROMOTION_NULL;
import static store.constants.uiConstants.OutputMessage.SHOW_CURRENT_STOCK_STATUS;
import static store.constants.uiConstants.OutputMessage.START_MESSAGE_FOR_RECEIPT;
import static store.constants.uiConstants.OutputMessage.WELCOME_TO_USER;

import java.util.List;
import store.dto.CurrentStockInfo;
import store.dto.UserReceipt;
import store.dto.UserOrderItem;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(WELCOME_TO_USER);
    }

    public void printProductsStatus(List<CurrentStockInfo> currentStockInfos) {
        printStoreStatusMessage();
        for (CurrentStockInfo currentStockInfo : currentStockInfos) {
            String promotionName = currentStockInfo.getPromotionName();
            if (!promotionName.equals(PROMOTION_NULL)) {
                printPromotionStatus(currentStockInfo);
            }
            printOriginalStatus(currentStockInfo);
        }
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    private void printStoreStatusMessage() {
        System.out.println(SHOW_CURRENT_STOCK_STATUS.getMessage());
    }

    private void printPromotionStatus(CurrentStockInfo currentStockInfo) {
        if (currentStockInfo.getPromotionQuantity() == 0) {
            System.out.println(currentStockInfo.toFormattedPromotionZeroStatus());
            return;
        }
        System.out.println(currentStockInfo.toFormattedPromotionStatus());
    }

    private void printOriginalStatus(CurrentStockInfo currentStockInfo) {
        if (currentStockInfo.getOriginalQuantity() == 0) {
            System.out.println(currentStockInfo.toFormattedOriginalZeroStatus());
            return;
        }
        System.out.println(currentStockInfo.toFormattedOriginalStatus());
    }

    public void printReceiptInfo(List<UserReceipt> receipt) {
        if (receipt.isEmpty()) {
            return;
        }
        printOrderReceiptInfo(receipt);
        printFreeGiftInfo(receipt);
    }

    public void printReceiptPrice(long totalQuantity, long totalPrice, long promotionDiscountPrice,
                                  int memberShipDiscountPrice) {
        if (totalQuantity == 0) {
            return;
        }
        printTotalPrice(totalQuantity, totalPrice);
        printPromotionDiscountPrice(promotionDiscountPrice);
        printMemberShipDiscountPrice(memberShipDiscountPrice);
        printUserHasToPay(totalPrice, promotionDiscountPrice, memberShipDiscountPrice);
    }

    private static void printOrderReceiptInfo(List<UserReceipt> receipt) {
        System.out.println(START_MESSAGE_FOR_RECEIPT);
        System.out.println("상품명                수량        금액");
        for (UserReceipt userItemReceipt : receipt) {
            System.out.printf("%s                %,d        %,d\n", userItemReceipt.getProductName(),
                    userItemReceipt.getQuantity(), userItemReceipt.getTotalPricePerProduct());
        }
    }

    private static void printFreeGiftInfo(List<UserReceipt> receipt) {
        System.out.println("=============증\t정===============");
        for (UserReceipt userItemReceipt : receipt) {
            UserOrderItem orderItem = userItemReceipt.getOrderItem();
            if (orderItem.getGiftQuantity() > 0) {
                System.out.printf("%s\t\t%,d \t\n", orderItem.getName(), orderItem.getGiftQuantity());
            }
        }
        System.out.println("====================================");
    }

    private static void printTotalPrice(long totalQuantity, long totalPrice) {
        System.out.printf("총구매액		%,d	      %,d\n", totalQuantity, totalPrice);
    }

    private static void printPromotionDiscountPrice(long promotionDiscountPrice) {
        System.out.printf("행사할인\t\t\t-%,d\n", promotionDiscountPrice);
    }

    private static void printMemberShipDiscountPrice(int memberShipDiscountPrice) {
        System.out.printf("멤버십할인\t\t\t-%,d\n", memberShipDiscountPrice);
    }

    private static void printUserHasToPay(long totalPrice, long promotionDiscountPrice, int memberShipDiscountPrice) {
        System.out.printf("내실돈\t\t\t %,d\n", totalPrice - promotionDiscountPrice - memberShipDiscountPrice);
    }
}
