package store.utils.parser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductsFileParserTest {

    private final ProductsFileParser productsFileParser;

    ProductsFileParserTest(ProductsFileParser productsFileParser) {
        this.productsFileParser = productsFileParser;
    }

    @DisplayName("프로모션이 적용된 상품을 가진 올바른 리스트가 입력되었을 때, 파싱에 성공한다")
    @Test
    void Given_ParsePromotionProductFile_When_ValidInput_Then_ReturnsPromotionProduct() {
        List<String> tempProduct = List.of("콜라", "1000", "5", "할인적용");

        assertThat(productsFileParser.getName(tempProduct)).isEqualTo("콜라");
        assertThat(productsFileParser.getPrice(tempProduct)).isEqualTo(1000);
        assertThat(productsFileParser.getPromotionQuantity(tempProduct)).isEqualTo(5);
        assertThat(productsFileParser.getPromotion(tempProduct)).isEqualTo("할인적용");
    }

    @DisplayName("프로모션 미적용 상품을 가진 올바른 리스트가 입력되었을 때, 파싱에 성공한다")
    @Test
    void Given_ParseOriginalProductFile_When_ValidInput_Then_ReturnsOriginalProduct() {
        List<String> tempProduct = List.of("사이다", "500", "10", "null");

        assertThat(productsFileParser.getName(tempProduct)).isEqualTo("사이다");
        assertThat(productsFileParser.getPrice(tempProduct)).isEqualTo(500);
        assertThat(productsFileParser.getOriginalQuantity(tempProduct)).isEqualTo(10);
        assertThat(productsFileParser.getPromotion(tempProduct)).isEqualTo("null");
    }

    @DisplayName("상품명이 빈 문자열일 경우 예외를 발생시킨다")
    @Test
    void Given_GetName_When_EmptyInput_Then_ThrowsException() {
        List<String> tempProduct = List.of("", "1500", "6", "할인적용");

        assertThatThrownBy(() -> productsFileParser.getName(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 파일의 값이 비어있습니다.");
    }

    @DisplayName("가격이 숫자가 아닌 경우 예외를 발생시킨다")
    @Test
    void Given_GetPrice_When_InvalidInputNonNumeric_Then_ThrowsException() {
        List<String> tempProduct = List.of("오렌지", "invalid", "3", "할인적용");

        assertThatThrownBy(() -> productsFileParser.getPrice(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자가 아닌 값이 들어있습니다.");
    }

    @DisplayName("가격이 정수 범위의 숫자가 아닌 경우 예외를 발생시킨다")
    @Test
    void Given_GetPrice_When_InvalidInputNotIntegerRange_Then_ThrowsException() {
        List<String> tempProduct = List.of("오렌지", "2147483648", "3", "할인적용");

        assertThatThrownBy(() -> productsFileParser.getPrice(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 정수범위의 가격이 아닙니다.");
    }

    @DisplayName("프로모션 수량이 숫자가 아닌 경우, 예외가 발생한다")
    @Test
    void Given_GetPromotionQuantity_When_InvalidInput_Then_ThrowsException() {
        List<String> tempProduct = List.of("참외", "1000", "invalid", "할인적용");

        assertThatThrownBy(() -> productsFileParser.getPromotionQuantity(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자가 아닌 값이 들어있습니다.");
    }

    @DisplayName("프로모션 적용이 되지 않은 수량이 숫자가 아닌 경우, 예외가 발생한다")
    @Test
    void Given_GetOriginalQuantity_When_InvalidInput_Then_ThrowsException() {
        List<String> tempProduct = List.of("참외", "1000", "invalid", "null");

        assertThatThrownBy(() -> productsFileParser.getOriginalQuantity(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자가 아닌 값이 들어있습니다.");
    }

    @DisplayName("프로모션 수량이 정수 범위의 숫자가 아닌 경우, 예외가 발생한다")
    @Test
    void Given_GetPromotionQuantity_When_InvalidInputNotIntegerRange_Then_ThrowsException() {
        List<String> tempProduct = List.of("참외", "1000", "2147483648", "할인적용");

        assertThatThrownBy(() -> productsFileParser.getPromotionQuantity(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 정수범위의 수량이 아닙니다.");
    }

    @DisplayName("프로모션 수량이 정수 범위의 숫자가 아닌 경우, 예외가 발생한다")
    @Test
    void Given_GetOriginalQuantity_When_InvalidInputNotIntegerRange_Then_ThrowsException() {
        List<String> tempProduct = List.of("참외", "1000", "2147483648", "null");

        assertThatThrownBy(() -> productsFileParser.getOriginalQuantity(tempProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 정수범위의 수량이 아닙니다.");
    }
}