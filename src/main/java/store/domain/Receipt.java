package store.domain;

import store.dto.UserReceipt;
import store.dto.UserOrderItem;

public class Receipt {

    private UserOrderItem orderItem;
    private int totalPricePerProduct;

    public Receipt(UserOrderItem orderItems, int totalPricePerProduct) {
        this.orderItem = orderItems;
        this.totalPricePerProduct = totalPricePerProduct;
    }

    public UserReceipt getOderItem() {
        return new UserReceipt(orderItem, totalPricePerProduct);
    }}
