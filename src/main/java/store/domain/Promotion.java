package store.domain;

import java.time.LocalDateTime;

public class Promotion {
    private String name;
    private int buyCount;
    private int getCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String name, int buyCount, int getCount, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public boolean hasActivePromotion(LocalDateTime localDateTime) {
        if (!(localDateTime.isBefore(startDate) && localDateTime.isAfter(endDate))) {
            return true;
        }
        return false;
    }

    public boolean isEnoughForAdditionalGift(int promotionQuantity, int orderQuantity) {
        int gapForQuantity = promotionQuantity - orderQuantity;
        return isGapEnoughForGift(gapForQuantity) && isPossibleForGift(orderQuantity);
    }

    public int getGiftQuantity(int quantity) {
        return quantity / (buyCount + getCount) * getCount;
    }

    private boolean isGapEnoughForGift(int gapForQuantity) {
        return gapForQuantity >= getCount;
    }

    public int getNonGiftQuantity(int orderQuantity, int promotionQuantity) {
        return orderQuantity - promotionQuantity / (buyCount + getCount) * (buyCount + getCount);
    }

    private boolean isPossibleForGift(int orderQuantity) {
        return orderQuantity % (buyCount + getCount) == buyCount;
    }
}
