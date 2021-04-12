package ge.tsu.spring.lecture4.student;

public class RecordAlreadyExistsException extends Exception {

    public RecordAlreadyExistsException(String message) {
        super(message);
    }

    public RecordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
