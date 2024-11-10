package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.dto.UserOrderItem;
import store.dto.UserOrderItems;

public class OrderItems {
    private List<OrderItem> orderItems;

    public OrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public UserOrderItems getUserOrderItems() {
        List<UserOrderItem> userOrderItems = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            userOrderItems.add(orderItem.getOderItem());
        }
        return new UserOrderItems(userOrderItems);
    }
}
