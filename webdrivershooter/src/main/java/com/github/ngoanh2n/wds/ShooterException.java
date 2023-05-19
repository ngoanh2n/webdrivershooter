package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;

/**
 * Runtime exception for WebDriverShooter.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class ShooterException extends RuntimeError {
    /**
     * {@inheritDoc}
     */
    public ShooterException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ShooterException(Throwable cause) {
        super(cause);
    }

    /**
     * {@inheritDoc}
     */
    public ShooterException(String message, Throwable cause) {
        super(message, cause);
    }
}
