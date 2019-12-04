package androidx.core.os;

public class OperationCanceledException extends RuntimeException {
    public OperationCanceledException() {
        this(null);
    }

    public OperationCanceledException(String message) {
        if (message == null) {
            message = "The operation has been canceled.";
        }
        super(message);
    }
}
