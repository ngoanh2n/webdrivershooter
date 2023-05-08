package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;

public class ShooterException extends RuntimeError {
    public ShooterException(String message) {
        super(message);
    }

    public ShooterException(Throwable cause) {
        super(cause);
    }

    public ShooterException(String message, Throwable cause) {
        super(message, cause);
    }
}
