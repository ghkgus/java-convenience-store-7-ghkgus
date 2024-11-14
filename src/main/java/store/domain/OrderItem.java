package store.domain;

import static store.domain.GiftItemType.NO_PROMOTION;

import java.time.LocalDateTime;
import store.dto.UserOrderItem;

public class OrderItem {
    private Product product;
    private String name;
    private int quantity;
    private final LocalDateTime localDateTime;

    public OrderItem(Product product, String name, int quantity, LocalDateTime localDateTime) {
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.localDateTime = localDateTime;
    }

    public UserOrderItem getOderItem() {
        GiftItemType type = getType();
        int giftQuantity = getGiftQuantity(type);
        int nonGiftQuantity = getNonGiftQuantity(type);
        return new UserOrderItem(name, type, quantity, giftQuantity, nonGiftQuantity);
    }

    private GiftItemType getType() {
        if (product.hasPromotion(localDateTime)) {
            return product.checkType(quantity);
        }
        return NO_PROMOTION;
    }

    private int getGiftQuantity(GiftItemType type) {
        if (type.equals(NO_PROMOTION)) {
            return 0;
        }
        return product.getGiftQuantity(type, quantity);
    }

    private int getNonGiftQuantity(GiftItemType type) {
        if (type.equals(NO_PROMOTION)) {
            return 0;
        }
        return product.getNonGiftQuantity(type, quantity);
    }
}
