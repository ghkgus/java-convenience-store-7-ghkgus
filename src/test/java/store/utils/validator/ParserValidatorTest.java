package store.utils.validator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParserValidatorTest {

    @DisplayName("비어있는 문자열이 입력되면 validateString 메서드에서 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void Given_EmptyString_When_ValidateString_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> ParserValidator.validateString(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 파일의 값이 비어있습니다.");
    }

    @DisplayName("숫자가 아닌 값이 포함된 문자열이 입력되면 validateNumber 메서드에서 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "1a00", "45.67", "-100", "123abc"})
    void Given_NonNumericString_When_ValidateNumber_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> ParserValidator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자가 아닌 값이 들어있습니다.");
    }

    @DisplayName("비어있는 문자열이 입력되면 validateNumber 메서드에서 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void Given_EmptyString_When_ValidateNumber_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> ParserValidator.validateNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 파일의 값이 비어있습니다.");
    }

    @DisplayName("비어있는 문자열이 입력되면 validateDate 메서드에서 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void Given_EmptyString_When_ValidateDate_Then_ThrowsException(String input) {
        assertThatThrownBy(() -> ParserValidator.validateDate(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 파일의 값이 비어있습니다.");
    }
}