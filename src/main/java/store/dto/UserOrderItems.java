package store.dto;

import java.util.List;

public class UserOrderItems {
    private  List<UserOrderItem> orderItems;

    public UserOrderItems(List<UserOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<UserOrderItem> getOrderItems() {
        return orderItems;
    }
}
