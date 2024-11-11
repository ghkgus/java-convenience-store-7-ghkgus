package store.constants;

public enum FileErrorMessage {
    INVALID_FILE("파일이 존재하지 않습니다."),
    UNREADABLE_FILE("[ERROR] 파일을 읽을 수 없습니다."),
    INVALID_FILE_CONTENT_FORM("[ERROR] 파일의 형식이 잘못되었습니다."),
    ;

    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + message;
    }}
