package store.view;

import static store.constants.ProductConstants.PROMOTION_NULL;
import static store.constants.uiConstants.OutputMessage.AMOUNT_DUE;
import static store.constants.uiConstants.OutputMessage.APPLIED_PROMOTION_PRICE;
import static store.constants.uiConstants.OutputMessage.DIVIDE_LINE;
import static store.constants.uiConstants.OutputMessage.FREE_GIFT;
import static store.constants.uiConstants.OutputMessage.GIFT_QUANTITY;
import static store.constants.uiConstants.OutputMessage.MEMBERSHIP_DISCOUNT_PRICE;
import static store.constants.uiConstants.OutputMessage.PRINT_FORMAT_FOR_ORDER;
import static store.constants.uiConstants.OutputMessage.PRODUCT_NAME_QUANTITY_PRICE;
import static store.constants.uiConstants.OutputMessage.SHOW_CURRENT_STOCK_STATUS;
import static store.constants.uiConstants.OutputMessage.START_MESSAGE_FOR_RECEIPT;
import static store.constants.uiConstants.OutputMessage.TOTAL_PRICE;
import static store.constants.uiConstants.OutputMessage.WELCOME_TO_USER;

import java.util.List;
import store.dto.CurrentStockInfo;
import store.dto.UserReceipt;
import store.dto.UserOrderItem;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(WELCOME_TO_USER.getMessage());
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

    private static void printOrderReceiptInfo(List<UserReceipt> receipt) {
        System.out.println(START_MESSAGE_FOR_RECEIPT.getMessage());
        System.out.println(PRODUCT_NAME_QUANTITY_PRICE.getMessage());
        for (UserReceipt userItemReceipt : receipt) {
            System.out.printf(PRINT_FORMAT_FOR_ORDER.getMessage(), userItemReceipt.getProductName(),
                    userItemReceipt.getQuantity(), userItemReceipt.getTotalPricePerProduct());
        }
    }

    private static void printFreeGiftInfo(List<UserReceipt> receipt) {
        System.out.println(GIFT_QUANTITY.getMessage());
        for (UserReceipt userItemReceipt : receipt) {
            UserOrderItem orderItem = userItemReceipt.getOrderItem();
            if (orderItem.getGiftQuantity() > 0) {
                System.out.printf(FREE_GIFT.getMessage(), orderItem.getName(), orderItem.getGiftQuantity());
            }
        }
        System.out.println(DIVIDE_LINE.getMessage());
    }

    private static void printTotalPrice(long totalQuantity, long totalPrice) {
        System.out.printf(TOTAL_PRICE.getMessage(), totalQuantity, totalPrice);
    }

    private static void printPromotionDiscountPrice(long promotionDiscountPrice) {
        System.out.printf(APPLIED_PROMOTION_PRICE.getMessage(), promotionDiscountPrice);
    }

    private static void printMemberShipDiscountPrice(int memberShipDiscountPrice) {
        System.out.printf(MEMBERSHIP_DISCOUNT_PRICE.getMessage(), memberShipDiscountPrice);
    }

    private static void printUserHasToPay(long totalPrice, long promotionDiscountPrice, int memberShipDiscountPrice) {
        System.out.printf(AMOUNT_DUE.getMessage(), totalPrice - promotionDiscountPrice - memberShipDiscountPrice);
    }
}
