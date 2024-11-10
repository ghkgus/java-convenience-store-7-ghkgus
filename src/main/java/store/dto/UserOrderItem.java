package store.dto;

public class UserOrderItem {
    private String name;
    private int quantity;

    public UserOrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
