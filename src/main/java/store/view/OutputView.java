package store.view;

import java.util.List;
import store.dto.CurrentStockInfo;
import store.dto.UserReceipt;
import store.dto.UserOrderItem;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public void printProductsStatus(List<CurrentStockInfo> currentStockInfos) {
        printStoreStatusMessage();
        for (CurrentStockInfo currentStockInfo : currentStockInfos) {
            String promotionName = currentStockInfo.getPromotionName();
            if (!promotionName.equals("null")) {
                printPromotionStatus(currentStockInfo);
            }
            printOriginalStatus(currentStockInfo);
        }
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    private void printStoreStatusMessage() {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
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
        printOrderReceiptInfo(receipt);
        printFreeGiftInfo(receipt);
    }

    public void printReceiptPrice(long totalQuantity, long totalPrice, long promotionDiscountPrice,
                                  int memberShipDiscountPrice) {
        printTotalPrice(totalQuantity, totalPrice);
        printPromotionDiscountPrice(promotionDiscountPrice);
        printMemberShipDiscountPrice(memberShipDiscountPrice);
        printUserHasToPay(totalPrice, promotionDiscountPrice, memberShipDiscountPrice);
    }

    private static void printOrderReceiptInfo(List<UserReceipt> receipt) {
        System.out.println("\n==============W 편의점================");
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
