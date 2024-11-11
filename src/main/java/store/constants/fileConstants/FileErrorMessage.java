package store.constants.fileConstants;

public enum FileErrorMessage {
    INVALID_FILE("파일이 존재하지 않습니다."),
    UNREADABLE_FILE("파일을 읽을 수 없습니다."),
    INVALID_FILE_CONTENT_FORM("파일의 형식이 잘못되었습니다."),

    IS_NOT_INTEGER_RANGE("정수범위의 가격이 아닙니다."),
    IS_NOT_CORRECT_DATE_FORM("날짜 형식이 올바르지 않습니다.")
    ;

    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }
}
