package jpabook.jpashop.exception;

public class NotEnoughStockExepion extends RuntimeException {
    public NotEnoughStockExepion(String message) {
        super(message);
    }

    public NotEnoughStockExepion(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockExepion(Throwable cause) {
        super(cause);
    }

    protected NotEnoughStockExepion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotEnoughStockExepion() {
        super();
    }
}
