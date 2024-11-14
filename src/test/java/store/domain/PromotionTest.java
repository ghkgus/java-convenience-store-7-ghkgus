package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {
    private final String name = "프로모션 적용";
    private final LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
    private final LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);
    private final int buyCount = 2;
    private final int getCount = 1;

    private final Promotion promotion = new Promotion(name, buyCount, getCount, startDate, endDate);

    @DisplayName("이름을 정확히 반환한다.")
    @Test
    void getName_ReturnsCorrectName() {
        assertThat(promotion.getName()).isEqualTo(name);
    }

    @DisplayName("프로모션 기간 내의 날짜일 경우 active 상태를 반환한다.")
    @Test
    void hasActivePromotion_ReturnsTrue_WhenWithinPromotionPeriod() {
        LocalDateTime activeDate = LocalDateTime.of(2024, 6, 15, 0, 0);
        assertThat(promotion.hasActivePromotion(activeDate)).isTrue();
    }

    @DisplayName("프로모션 시작 날짜 이전의 날짜일 경우 active 상태가 아니다.")
    @Test
    void hasActivePromotion_ReturnsFalse_WhenBeforePromotionPeriod() {
        LocalDateTime beforeStartDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        assertThat(promotion.hasActivePromotion(beforeStartDate)).isFalse();
    }

    @DisplayName("프로모션 종료 날짜 이후의 날짜로 active 상태가 아니다.")
    @Test
    void hasActivePromotion_ReturnsFalse_WhenAfterPromotionPeriod() {
        LocalDateTime afterEndDate = LocalDateTime.of(2025, 1, 1, 0, 0);
        assertThat(promotion.hasActivePromotion(afterEndDate)).isFalse();
    }

    @DisplayName("추가 제공이 가능한 주문 수량과 프로모션 수량이 주어졌을 때 true를 반환한다")
    @Test
    void isEnoughForAdditionalGift_ReturnsTrue_WhenGapIsEnough() {
        int promotionQuantity = 10;
        int orderQuantity = 8;
        assertThat(promotion.isEnoughForAdditionalGift(promotionQuantity, orderQuantity)).isTrue();
    }

    @DisplayName("추가 제공이 불가능한 주문 수량과 프로모션 수량이 주어졌을 때 false를 반환한다.")
    @Test
    void isEnoughForAdditionalGift_ReturnsFalse_WhenGapIsNotEnough() {
        int promotionQuantity = 10;
        int orderQuantity = 9;
        assertThat(promotion.isEnoughForAdditionalGift(promotionQuantity, orderQuantity)).isFalse();
    }

    @DisplayName("구매 시 제공되는 무료 수량을 정확히 계산하여 반환한다.")
    @Test
    void getGiftQuantity_ReturnsCorrectGiftQuantity() {
        int orderQuantity = 6;
        assertThat(promotion.getGiftQuantity(orderQuantity)).isEqualTo(2);
    }

    @DisplayName("무료로 제공되지 않는 정확한 수량을 반환한다.")
    @Test
    void getNonGiftQuantity_ReturnsCorrectNonGiftQuantity() {
        int orderQuantity = 10;
        int promotionQuantity = 3;
        assertThat(promotion.getNonGiftQuantity(orderQuantity, promotionQuantity)).isEqualTo(7);
    }
}
