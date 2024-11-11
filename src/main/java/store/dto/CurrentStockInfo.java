package store.dto;

import store.domain.Promotion;

public class CurrentStockInfo {

    private String name;
    private int price;
    private int originalQuantity;
    private int promotionQuantity;
    private Promotion promotion;

    public CurrentStockInfo(String name, int price, int originalQuantity, int promotionQuantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.originalQuantity = originalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.promotion = promotion;
    }

    public String toFormattedPromotionZeroStatus() {
        return String.format("- %s %,d원 %s %s", name, price, "재고 없음", promotion.getName());
    }

    public String toFormattedPromotionStatus() {
        return String.format("- %s %,d원 %,d개 %s", name, price, promotionQuantity, promotion.getName());
    }

    public String toFormattedOriginalZeroStatus() {
        return String.format("- %s %,d원 %s %s", name, price, "재고 없음", "");
    }

    public String toFormattedOriginalStatus() {
        return String.format("- %s %,d원 %,d개 %s", name, price, originalQuantity, "");
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public String getPromotionName() {
        return promotion.getName();
    }
}
