package store.domain;


import static store.domain.GiftItemType.APPLIED_PROMOTION_WITHOUT_GIFT;
import static store.domain.GiftItemType.APPLIED_PROMOTION_WITH_GIFT;
import static store.domain.GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE;

import java.time.LocalDateTime;
import store.dto.CurrentStockInfo;

public class Product {
    private String name;
    private int price;
    private int originalQuantity;
    private int promotionQuantity;
    private Promotion promotion;

    public Product(String name, int price, int originalQuantity, int promotionQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.originalQuantity = originalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public void updatePromotionProduct(int quantity, Promotion appliedPromotion) {
        promotionQuantity += quantity;
        promotion = appliedPromotion;
    }

    public void updateOriginalProduct(int quantity) {
        originalQuantity += quantity;
    }

    public CurrentStockInfo getCurrentStockInfo() {
        String tempName = name;
        int tempPrice = price;
        int tempOriginalQuantity = originalQuantity;
        int tempPromotionQuantity = promotionQuantity;
        Promotion tempPromotion = promotion;

        return new CurrentStockInfo(tempName, tempPrice, tempOriginalQuantity, tempPromotionQuantity, tempPromotion);
    }

    public boolean hasPromotion(LocalDateTime localDateTime) {
        if (promotion.getName().equals("null")) {
            return false;
        }
        return promotion.hasActivePromotion(localDateTime);
    }

    public GiftItemType checkType(int orderQuantity) {
        if (orderQuantity > promotionQuantity) {
            return PARTIAL_PROMOTION_WITH_REGULAR_PRICE;
        }
        return checkExtraGift(orderQuantity);
    }

    public int getGiftQuantity(GiftItemType type, int orderQuantity) {
        if (type.equals(PARTIAL_PROMOTION_WITH_REGULAR_PRICE)) {
            return promotion.getGiftQuantity(promotionQuantity);
        }
        return promotion.getGiftQuantity(orderQuantity);
    }

    public int getNonGiftQuantity(GiftItemType type, int orderQuantity) {
        if (type.equals(PARTIAL_PROMOTION_WITH_REGULAR_PRICE)) {
            return promotion.getNonGiftQuantity(orderQuantity, promotionQuantity);
        }
        return 0;
    }

    public void updateStockForNonPromotion(int quantity) {
        originalQuantity -= quantity;
    }

    public void updateStockForPartialPromotion(int quantity) {
        originalQuantity -= (quantity - promotionQuantity);
        promotionQuantity = 0;
    }

    public void updateStockForPromotion(int totalQuantity) {
        promotionQuantity -= totalQuantity;
    }

    private GiftItemType checkExtraGift(int orderQuantity) {
        if (promotion.isEnoughForAdditionalGift(promotionQuantity, orderQuantity)) {
            return APPLIED_PROMOTION_WITH_GIFT;
        }
        return APPLIED_PROMOTION_WITHOUT_GIFT;
    }
}
