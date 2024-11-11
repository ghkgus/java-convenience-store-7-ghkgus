package store.dto;

import store.domain.GiftItemType;

public class UserOrderItem {

    private String name;
    private GiftItemType type;
    private int totalQuantity;
    private int giftQuantity;
    private int nonGiftQuantity;


    public UserOrderItem(String name, GiftItemType type, int quantity, int giftQuantity, int nonGiftQuantity) {
        this.name = name;
        this.type = type;
        this.totalQuantity = quantity;
        this.giftQuantity = giftQuantity;
        this.nonGiftQuantity = nonGiftQuantity;
    }

    public String getName() {
        return name;
    }

    public GiftItemType getType() {
        return type;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getGiftQuantity() {
        return giftQuantity;
    }

    public int getNonGiftQuantity() {
        return nonGiftQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setGiftQuantity(int giftQuantity) {
        this.giftQuantity = giftQuantity;
    }
}
