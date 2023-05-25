package dev.b37.libs.browsersupport.exception;

public class BrowserSupportException extends RuntimeException {

    public BrowserSupportException() {
        super();
    }

    public BrowserSupportException(String message) {
        super(message);
    }

    public BrowserSupportException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowserSupportException(Throwable cause) {
        super(cause);
    }
}
