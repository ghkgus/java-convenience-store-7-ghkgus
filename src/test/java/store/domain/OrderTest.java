package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @DisplayName("주문 생성 시 이름과 수량이 올바르게 설정된다.")
    @Test
    void Given_ValidOrder_When_CreateOrder_Then_ProperlySetValues() {
        String productName = "상품";
        int quantity = 10;

        Order order = new Order(productName, quantity);

        assertThat(order.getName()).isEqualTo(productName);
        assertThat(order.getQuantity()).isEqualTo(quantity);
    }

    @DisplayName("수량 추가 시 수량이 정확히 증가한다.")
    @Test
    void Given_Order_When_AddQuantity_Then_QuantityIncreasesCorrectly() {
        String productName = "상품";
        int initialQuantity = 10;
        int additionalQuantity = 5;

        Order order = new Order(productName, initialQuantity);
        order.addQuantity(additionalQuantity);

        assertThat(order.getQuantity()).isEqualTo(initialQuantity + additionalQuantity);
    }
}
