package store.utils.parser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PromotionsFileParserTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @DisplayName("유효한 프로모션 데이터가 입력되었을 때, Promotion 객체를 올바르게 생성한다.")
    @Test
    void Given_ValidPromotionData_When_Parse_Then_CreatesPromotion() {
        List<String> tempPromotion = List.of("프로모션", "1", "1", "2023-11-01", "2023-12-01");

        assertThat(PromotionsFileParser.getName(tempPromotion)).isEqualTo("프로모션");
        assertThat(PromotionsFileParser.getBuyQuantity(tempPromotion)).isEqualTo(1);
        assertThat(PromotionsFileParser.getGetQuantity(tempPromotion)).isEqualTo(1);
        assertThat(PromotionsFileParser.getStartTime(tempPromotion, FORMATTER)).isEqualTo(LocalDateTime.parse("2023-11-01T00:00:00"));
        assertThat(PromotionsFileParser.getEndTime(tempPromotion, FORMATTER)).isEqualTo(LocalDateTime.parse("2023-12-01T23:59:59"));
    }

    @DisplayName("유효하지 않은 이름이 입력되었을 때, 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void Given_InvalidName_When_Parse_Then_ThrowsException(String invalidName) {
        List<String> tempPromotion = List.of(invalidName, "1", "1", "2023-11-01", "2023-12-01");

        assertThatThrownBy(() -> PromotionsFileParser.getPromotions(tempPromotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효하지 않은 수량이 입력되었을 때, 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "-5"})
    void Given_InvalidQuantity_When_Parse_Then_ThrowsException(String invalidQuantity) {
        List<String> tempPromotion = List.of("프로모션", invalidQuantity, "1", "2023-11-01", "2023-12-01");

        assertThatThrownBy(() -> PromotionsFileParser.getPromotions(tempPromotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정수 범위가 아닌 수량이 입력되었을 때, 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"2147483648", "98765432123456789"})
    void Given_InputNotIntegerRange_When_Parse_thenThrowsException(String invalidQuantity) {
        List<String> tempPromotion = List.of("프로모션", invalidQuantity, "1", "2023-11-01", "2023-12-01");

        assertThatThrownBy(() -> PromotionsFileParser.getPromotions(tempPromotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효하지 않은 시작 날짜가 입력되었을 때, 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"2023/11/01", "2023-13-01", "2023-11-32"})
    void Given_InvalidStartDate_When_Parse_Then_ThrowsException(String invalidDate) {
        List<String> tempPromotion = List.of("프로모션", "1", "1", invalidDate, "2023-12-01");

        assertThatThrownBy(() -> PromotionsFileParser.getPromotions(tempPromotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("유효하지 않은 종료 날짜가 입력되었을 때, 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"2023/12/01", "2023-00-01", "2023-11-32"})
    void Given_InvalidEndDate_When_Parse_Then_ThrowsException(String invalidDate) {
        List<String> tempPromotion = List.of("프로모션", "1", "1", "2023-11-01", invalidDate);

        assertThatThrownBy(() -> PromotionsFileParser.getPromotions(tempPromotion))
                .isInstanceOf(IllegalArgumentException.class);
    }
}