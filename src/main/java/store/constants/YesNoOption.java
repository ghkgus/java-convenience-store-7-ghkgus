package store.constants;

public enum YesNoOption {
    Y("Y", true),
    N("N", false),
    ;

    private String userAnswer;
    private boolean userAnswerType;

    YesNoOption(String userAnswer, boolean userAnswerType) {
        this.userAnswer = userAnswer;
        this.userAnswerType = userAnswerType;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public boolean isUserAnswerType() {
        return userAnswerType;
    }
}
