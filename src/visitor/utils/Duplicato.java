package visitor.utils;

public class Duplicato extends RuntimeException {
    public Duplicato(String message) {
        super(message);
    }

    public Duplicato(String message, Throwable cause) {
        super(message, cause);
    }
}
