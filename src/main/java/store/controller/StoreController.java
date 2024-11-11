package store.controller;

import static store.constants.YesNoOption.Y;
import static store.domain.GiftItemType.APPLIED_PROMOTION_WITHOUT_GIFT;
import static store.domain.GiftItemType.NO_PROMOTION;
import static store.domain.GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE;

import java.util.ArrayList;
import java.util.List;
import store.domain.OrderItems;
import store.dto.CurrentStockInfo;
import store.dto.UserReceipt;
import store.dto.UserOrderItem;
import store.dto.UserOrderItems;
import store.service.OrderService;
import store.service.ProductService;
import store.utils.validator.AnswerValidator;
import store.utils.validator.OrderValidator;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private InputView inputView;
    private OutputView outputView;
    private OrderService orderService;
    private ProductService productService;

    public StoreController(InputView inputView, OutputView outputView, OrderService orderService,
                           ProductService productService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderService = orderService;
        this.productService = productService;
    }

    public void run() {
        while (true) {
            welcomeUser();
            OrderItems orderItems = getUserOrder();
            List<UserOrderItem> orderItemsAppliedPromotion = applyPromotionToOrder(orderItems);
            showReceipt(orderItemsAppliedPromotion);
            updateStockStatus(orderItemsAppliedPromotion);
            if (!wantToRepurchase()) {
                break;
            }
        }
    }

    private void welcomeUser() {
        outputView.printWelcomeMessage();
        List<CurrentStockInfo> currentStockInfos = productService.getCurrentStockInfos();
        outputView.printProductsStatus(currentStockInfos);
    }

    private OrderItems getUserOrder() {
        while (true) {
            try {
                String userOrderItem = inputView.getUserOrder();
                OrderValidator.validateUserOrder(userOrderItem);
                return orderService.createOrder(userOrderItem);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private List<UserOrderItem> applyPromotionToOrder(OrderItems orderItems) {
        List<UserOrderItem> finalOrderItems = new ArrayList<>();
        UserOrderItems userOrderItems = orderItems.getUserOrderItems();

        for (UserOrderItem orderItem : userOrderItems.getOrderItems()) {
            addItemAccordingToType(orderItem, finalOrderItems);
        }

        return finalOrderItems;
    }

    private void addItemAccordingToType(UserOrderItem orderItem, List<UserOrderItem> finalOrderItems) {
        if (orderItem.getType() == NO_PROMOTION || orderItem.getType() == APPLIED_PROMOTION_WITHOUT_GIFT) {
            finalOrderItems.add(orderItem);
            return;
        }
        if (orderItem.getType() == PARTIAL_PROMOTION_WITH_REGULAR_PRICE) {
            askUserForExtraPrice(orderItem, finalOrderItems);
            return;
        }
        askUserForFreeGift(orderItem, finalOrderItems);
    }

    private void askUserForExtraPrice(UserOrderItem orderItem, List<UserOrderItem> finalOrderItems) {
        while (true) {
            try {
                getAnswerForExtraPrice(orderItem, finalOrderItems);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void getAnswerForExtraPrice(UserOrderItem orderItem, List<UserOrderItem> finalOrderItems) {
        String answer = inputView.askUserForRegularPricePayment(orderItem.getName(), orderItem.getNonGiftQuantity());
        AnswerValidator.validateUserAnswer(answer);
        if (answer.equals(Y.getUserAnswer())) {
            finalOrderItems.add(orderItem);
        }
    }

    private void askUserForFreeGift(UserOrderItem orderItem, List<UserOrderItem> finalOrderItems) {
        while (true) {
            try {
                getAnswerForFreeGift(orderItem, finalOrderItems);
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void getAnswerForFreeGift(UserOrderItem orderItem, List<UserOrderItem> finalOrderItems) {
        String answer = inputView.askUserForExtraGift(orderItem.getName());
        AnswerValidator.validateUserAnswer(answer);
        if (answer.equals(Y.getUserAnswer())) {
            orderItem.setGiftQuantity(orderItem.getGiftQuantity() + 1);
            orderItem.setTotalQuantity(orderItem.getTotalQuantity() + 1);
            finalOrderItems.add(orderItem);
        }
    }

    private void showReceipt(List<UserOrderItem> orderItems) {
        long totalPrice = productService.calculateTotalPrice(orderItems);
        long totalQuantity = productService.calculateTotalQuantity(orderItems);
        long promotionDiscountPrice = productService.calculatePromotionDiscount(orderItems);
        int memberShipDiscountPrice = 0;
        if (applyMemberShipToOrder()) {
            memberShipDiscountPrice = productService.calculateMembershipDiscount(orderItems);
        }
        List<UserReceipt> receipt = productService.calculateOrderProductPrice(orderItems);
        outputView.printReceiptInfo(receipt);
        outputView.printReceiptPrice(totalQuantity, totalPrice, promotionDiscountPrice, memberShipDiscountPrice);
    }

    private boolean applyMemberShipToOrder() {
        while (true) {
            try {
                String answer = inputView.askUserToApplyMembership();
                AnswerValidator.validateUserAnswer(answer);
                return answer.equals(Y.getUserAnswer());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void updateStockStatus(List<UserOrderItem> orderItems) {
        productService.updateStockAfterSale(orderItems);
    }

    private boolean wantToRepurchase() {
        while (true) {
            try {
                String answer = inputView.askUserToRepurchase();
                AnswerValidator.validateUserAnswer(answer);
                return answer.equals(Y.getUserAnswer());
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
