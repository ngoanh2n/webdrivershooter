package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;

/**
 * Runtime exception for {@link WebDriverShooter}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class ShooterException extends RuntimeError {
    /**
     * Construct a new runtime exception with the specified detail message.
     *
     * @param message The detail message, is saved for later retrieval by the {@link #getMessage()} method.
     */
    public ShooterException(String message) {
        super(message);
    }

    /**
     * Construct a new runtime exception with the specified cause and a detail message.
     *
     * @param cause The cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A {@code null} value is
     *              permitted, and indicates that the cause is nonexistent or unknown).
     */
    public ShooterException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct a new runtime exception with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ShooterException(String message, Throwable cause) {
        super(message, cause);
    }
}
