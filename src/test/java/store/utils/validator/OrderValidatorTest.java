package store.utils.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static store.constants.ErrorMessage.INVALID_FORM;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderValidatorTest {

    @DisplayName("연속된 콤마가 있는 문자열이 입력되면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"[상품명-1],,[상품명-2]", "[상품명-1],,[상품명-2]"})
    void Given_ConsecutiveCommas_When_ValidateUserOrder_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> OrderValidator.validateUserOrder(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_FORM.getMessage());
    }

    @DisplayName("빈 이름이 포함된 주문 아이템 리스트가 입력되면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void Given_EmptyNameInOrderItem_When_ValidateUserOrderForm_Then_ThrowsException(String name) {
        List<String> invalidOrder = List.of(name, "2");
        assertThatThrownBy(() -> OrderValidator.validateUserOrderForm(invalidOrder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_FORM.getMessage());
    }

    @DisplayName("수량이 비어있거나 숫자가 아닌 값이 포함된 주문 아이템 리스트가 입력되면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "abc", "  ", "-11223"})
    void Given_InvalidQuantityInOrderItem_When_ValidateUserOrderForm_Then_ThrowsException(String quantity) {
        List<String> invalidOrder = List.of("상품명", quantity);
        assertThatThrownBy(() -> OrderValidator.validateUserOrderForm(invalidOrder))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_FORM.getMessage());
    }

    @DisplayName("잘못된 포맷의 문자열이 입력되면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"상품,2", "상품[]", "상품{}"})
    void Given_InvalidFormat_When_ValidateCorrectForm_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> OrderValidator.validateCorrectForm(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_FORM.getMessage());
    }
    @DisplayName("정상적인 문자열이 입력되면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"상품1,2", "상품2,5", "상품3,10"})
    void Given_ValidString_When_ValidateUserOrder_Then_DoesNotThrowException(String input) {
        assertThatCode(() -> OrderValidator.validateUserOrder(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("정상적인 주문 아이템 리스트가 입력되면 예외가 발생하지 않는다.")
    @Test
    void Given_ValidOrderItem_When_ValidateUserOrderForm_Then_DoesNotThrowException() {
        List<String> validOrder = List.of("상품1", "5");
        assertThatCode(() -> OrderValidator.validateUserOrderForm(validOrder))
                .doesNotThrowAnyException();
    }

    @DisplayName("올바른 포맷의 문자열이 입력되면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"[상품,2]", "[상품,10]", "[상품,5]"})
    void Given_ValidFormat_When_ValidateCorrectForm_Then_DoesNotThrowException(String input) {
        assertThatCode(() -> OrderValidator.validateCorrectForm(input))
                .doesNotThrowAnyException();
    }
}