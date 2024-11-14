package store.dto;

public class UserReceipt {

    private UserOrderItem orderItem;
    private int totalPricePerProduct;

    public UserReceipt(UserOrderItem orderItem, int totalPricePerProduct) {
        this.orderItem = orderItem;
        this.totalPricePerProduct = totalPricePerProduct;
    }

    public UserOrderItem getOrderItem() {
        return orderItem;
    }

    public String getProductName() {
        return orderItem.getName();
    }

    public int getQuantity() {
        return orderItem.getTotalQuantity();
    }

    public int getTotalPricePerProduct() {
        return totalPricePerProduct;
    }
}
