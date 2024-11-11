package store.utils.validator;

import static store.constants.ErrorMessage.INVALID_ANSWER_TYPE;
import static store.constants.YesNoOption.N;
import static store.constants.YesNoOption.Y;

public class AnswerValidator {

    public static void validateUserAnswer(String answer) {
        if (!(answer.equals(Y.getUserAnswer()) || answer.equals(N.getUserAnswer()))) {
            throw new IllegalArgumentException(INVALID_ANSWER_TYPE.getMessage());
        }
    }
}
