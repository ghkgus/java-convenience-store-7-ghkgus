package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.CurrentStockInfo;

class ProductTest {
    private final String name = "상품";
    private final int price = 1000;
    private final int originalQuantity = 10;
    private final int promotionQuantity = 5;
    private final Promotion promotion = new Promotion("특가", 2, 1, LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2024, 12, 31, 23, 59));

    private final Product product = new Product(name, price, originalQuantity, promotionQuantity, promotion);

    @DisplayName("일반 상품 업데이트 시 재고 수량이 업데이트된다.")
    @Test
    void updateOriginalProduct_UpdatesOriginalQuantity() {
        int additionalQuantity = 15;

        product.updateOriginalProduct(additionalQuantity);

        assertThat(product.getCurrentStockInfo().getOriginalQuantity()).isEqualTo(originalQuantity + additionalQuantity);
    }

    @DisplayName("현재 재고 정보를 정확히 반환한다.")
    @Test
    void getCurrentStockInfo_ReturnsCorrectStockInfo() {
        CurrentStockInfo stockInfo = product.getCurrentStockInfo();

        assertThat(stockInfo.getName()).isEqualTo(name);
        assertThat(stockInfo.getPrice()).isEqualTo(price);
        assertThat(stockInfo.getOriginalQuantity()).isEqualTo(originalQuantity);
        assertThat(stockInfo.getPromotionQuantity()).isEqualTo(promotionQuantity);
    }

    @DisplayName("프로모션이 활성 상태일 때 true를 반환한다.")
    @Test
    void hasPromotion_ReturnsTrue_WhenPromotionIsActive() {
        LocalDateTime activeDate = LocalDateTime.of(2024, 11, 15, 0, 0);
        assertThat(product.hasPromotion(activeDate)).isTrue();
    }

    @DisplayName("프로모션이 비활성 상태일 때 false를 반환한다.")
    @Test
    void hasPromotion_ReturnsFalse_WhenPromotionIsInactive() {
        LocalDateTime inactiveDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertThat(product.hasPromotion(inactiveDate)).isFalse();
    }

    @DisplayName("주문 수량이 프로모션 수량보다 클 때 PARTIAL_PROMOTION_WITH_REGULAR_PRICE 타입을 반환한다.")
    @Test
    void checkType_ReturnsPartialPromotionWithRegularPrice_WhenOrderQuantityExceedsPromotionQuantity() {
        int orderQuantity = 15;
        assertThat(product.checkType(orderQuantity)).isEqualTo(GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE);
    }

    @DisplayName("주문 수량이 프로모션 추가 조건을 만족할 때 APPLIED_PROMOTION_WITH_GIFT 타입을 반환한다.")
    @Test
    void checkType_ReturnsAppliedPromotionWithGift_WhenOrderQuantityQualifiesForGift() {
        int orderQuantity = 2;
        assertThat(product.checkType(orderQuantity)).isEqualTo(GiftItemType.APPLIED_PROMOTION_WITH_GIFT);
    }

    @DisplayName("주문 수량이 추가 조건을 만족하지 않을 때 APPLIED_PROMOTION_WITHOUT_GIFT 타입을 반환한다.")
    @Test
    void checkType_ReturnsAppliedPromotionWithoutGift_WhenOrderQuantityDoesNotQualifyForGift() {
        int orderQuantity = 3;
        assertThat(product.checkType(orderQuantity)).isEqualTo(GiftItemType.APPLIED_PROMOTION_WITHOUT_GIFT);
    }

    @DisplayName("PARTIAL_PROMOTION_WITH_REGULAR_PRICE 타입일 때 선물 수량을 반환한다.")
    @Test
    void getGiftQuantity_ReturnsGiftQuantity_ForPartialPromotionWithRegularPrice() {
        int orderQuantity = 6;
        assertThat(product.getGiftQuantity(GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE, orderQuantity))
                .isEqualTo(promotion.getGiftQuantity(promotionQuantity));
    }

    @DisplayName("APPLIED_PROMOTION_WITH_GIFT 타입일 때 free 수량을 반환한다.")
    @Test
    void getGiftQuantity_ReturnsGiftQuantity_ForAppliedPromotionWithGift() {
        int orderQuantity = 3;
        assertThat(product.getGiftQuantity(GiftItemType.APPLIED_PROMOTION_WITH_GIFT, orderQuantity))
                .isEqualTo(promotion.getGiftQuantity(orderQuantity));
    }

    @DisplayName("PARTIAL_PROMOTION_WITH_REGULAR_PRICE 타입일 때 일반 수량을 반환한다.")
    @Test
    void getNonGiftQuantity_ReturnsNonGiftQuantity_ForPartialPromotionWithRegularPrice() {
        int orderQuantity = 6;
        assertThat(product.getNonGiftQuantity(GiftItemType.PARTIAL_PROMOTION_WITH_REGULAR_PRICE, orderQuantity))
                .isEqualTo(promotion.getNonGiftQuantity(orderQuantity, promotionQuantity));
    }

    @DisplayName("선물 미적용 시 원본 재고에서 수량을 차감한다.")
    @Test
    void updateStockForNonPromotion_DecreasesOriginalQuantity() {
        int quantity = 5;
        product.updateStockForNonPromotion(quantity);

        assertThat(product.getCurrentStockInfo().getOriginalQuantity()).isEqualTo(originalQuantity - quantity);
    }

    @DisplayName("부분 프로모션 시 원본 재고와 프로모션 재고를 정확히 차감한다.")
    @Test
    void updateStockForPartialPromotion_DecreasesOriginalAndPromotionQuantities() {
        int quantity = 12;
        product.updateStockForPartialPromotion(quantity);

        assertThat(product.getCurrentStockInfo().getOriginalQuantity()).isEqualTo(originalQuantity - (quantity - promotionQuantity));
        assertThat(product.getCurrentStockInfo().getPromotionQuantity()).isEqualTo(0);
    }
}
