package store.service;

import static store.constants.ErrorMessage.INVALID_PRODUCT_NAME;
import static store.constants.ErrorMessage.INVALID_INPUT;
import static store.constants.ErrorMessage.OVER_STOCK_QUANTITY;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.domain.Order;
import store.domain.OrderItem;
import store.domain.OrderItems;
import store.domain.Product;
import store.dto.CurrentStockInfo;
import store.repository.ProductRepository;
import store.utils.validator.OrderValidator;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderItems createOrder(String userOrderItem) {
        List<String> beforeCheckOrderItems = Arrays.asList(userOrderItem.trim().split(","));
        return makeOrder(beforeCheckOrderItems);
    }

    private OrderItems makeOrder(List<String> beforeCheckOrderItems) {
        LinkedHashMap<String, Order> afterCheckOrderItems = new LinkedHashMap<>();

        for (String beforeCheckOrderItem : beforeCheckOrderItems) {
            OrderValidator.validateCorrectForm(beforeCheckOrderItem);
            List<String> orderItem = getOrderItem(beforeCheckOrderItem);
            addOrder(orderItem, afterCheckOrderItems);
        }
        checkItemQuantityInStore(afterCheckOrderItems);
        return convertToOrderItems(afterCheckOrderItems);
    }

    private OrderItems convertToOrderItems(LinkedHashMap<String, Order> afterCheckOrderItems) {
        Set<String> names = afterCheckOrderItems.keySet();
        List<OrderItem> orderItems = new ArrayList<>();
        for (String name : names) {
            Product product = productRepository.findByKey(name);
            OrderItem orderItem = new OrderItem(product, name, afterCheckOrderItems.get(name).getQuantity(), DateTimes.now());
            orderItems.add(orderItem);
        }
        return new OrderItems(orderItems);
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

        int quantity = orderQuantity(orderItem.getLast());
        if (quantity == 0) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
        }
        addOrderQuantity(afterCheckOrderItems, productName, quantity);
    }

    private int orderQuantity(String orderQuantity) {
        try {
            return Integer.parseInt(orderQuantity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_INPUT.getMessage());
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
            Product product = productRepository.findByKey(productName);
            CurrentStockInfo currentStockInfo = product.getCurrentStockInfo();
            int productQuantity = currentStockInfo.getOriginalQuantity() + currentStockInfo.getPromotionQuantity();
            if (productQuantity < userQuantity) {
                throw new IllegalArgumentException(OVER_STOCK_QUANTITY.getMessage());
            }
        }
    }
}
