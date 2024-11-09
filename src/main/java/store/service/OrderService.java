package store.service;

import static store.constants.ErrorMessage.INVALID_PRODUCT_NAME;
import static store.constants.ErrorMessage.INVALID_PRODUCT_QUANTITY;
import static store.constants.ErrorMessage.OVER_STOCK_QUANTITY;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.Order;
import store.repository.ProductRepository;
import store.utils.validator.OrderValidator;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public HashMap<String, Order> createOrder(String userOrderItem) {
        List<String> beforeCheckOrderItems = Arrays.asList(userOrderItem.trim().split(","));
        return makeOrder(beforeCheckOrderItems);
    }

    private HashMap<String, Order> makeOrder(List<String> beforeCheckOrderItems) {
        HashMap<String, Order> afterCheckOrderItems = new HashMap<>();
        for (String beforeCheckOrderItem : beforeCheckOrderItems) {
            OrderValidator.validateCorrectForm(beforeCheckOrderItem);
            List<String> orderItem = getOrderItem(beforeCheckOrderItem);
            addOrder(orderItem, afterCheckOrderItems);
        }
        checkItemQuantityInStore(afterCheckOrderItems);
        return afterCheckOrderItems;
    }

    private List<String> getOrderItem(String beforeCheckOrderItem) {
        String itemWithoutBrackets = beforeCheckOrderItem.substring(1, beforeCheckOrderItem.length() - 1);
        List<String> orderItem = Arrays.asList(itemWithoutBrackets.split("-"));

        OrderValidator.validateUserOrderForm(orderItem);
        return orderItem;
    }

    private void addOrder(List<String> orderItem, HashMap<String, Order> afterCheckOrderItems) {
        String productName = orderItem.getFirst();
        checkItemNameInStore(productName);

        int quantity = orderQuantity(orderItem);
        if (quantity == 0) {
            throw new IllegalArgumentException(INVALID_PRODUCT_QUANTITY.getMessage());
        }
        addOrderQuantity(afterCheckOrderItems, productName, quantity);
    }

    private int orderQuantity(List<String> orderItem) {
        try {
            return Integer.parseInt(orderItem.getLast());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_PRODUCT_QUANTITY.getMessage());
        }
    }

    private void checkItemNameInStore(String productName) {
        if (!productRepository.containsKey(productName)) {
            throw new IllegalArgumentException(INVALID_PRODUCT_NAME.getMessage());
        }
    }

    private void addOrderQuantity(Map<String, Order> afterCheckOrderItems, String productName, int quantity) {
        afterCheckOrderItems.merge(productName, new Order(productName, quantity), (existingOrder, newOrder) -> {
            existingOrder.addQuantity(newOrder.getQuantity());
            return existingOrder;
        });
    }

    private void checkItemQuantityInStore(Map<String, Order> afterCheckOrderItems) {
        for (Order order : afterCheckOrderItems.values()) {
            String productName = order.getName();
            int userQuantity = order.getQuantity();
            int productQuantity = productRepository.getStockQuantity(productName);

            if (productQuantity < userQuantity) {
                throw new IllegalArgumentException(OVER_STOCK_QUANTITY.getMessage());
            }
        }
    }

}
