package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;
import org.openqa.selenium.WebDriver;

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

    //===============================================================================//

    /**
     * Runtime exception when {@link WebDriverShooter} could not detect any {@link WebDriver}<br>
     * via {@link WebDriverProvider#provide()}<br>
     * or {@link WebDriverShooter#shoot(WebDriverShooter, ShooterOptions, WebDriver...)}.
     */
    public static final class NoneDriver extends ShooterException {
        /**
         * Construct a new {@link NullDriverPassed} with the specified detail message.
         */
        public NoneDriver() {
            super("No WebDriver is passed or provided");
        }
    }

    /**
     * Runtime exception when the found {@link WebDriver} is closed.
     */
    public static final class ClosedDriver extends ShooterException {
        /**
         * Construct a new {@link NullDriverPassed} with the specified detail message.
         */
        public ClosedDriver() {
            super("The WebDriver is closed");
        }
    }

    /**
     * Runtime exception when the provided {@link WebDriver} is closed.
     */
    public static final class ClosedDriverProvided extends ShooterException {
        /**
         * Construct a new {@link NullDriverPassed} with the specified detail message.
         */
        public ClosedDriverProvided() {
            super("The provided WebDriver is closed");
        }
    }

    /**
     * Runtime exception when you have provided a null {@link WebDriver}<br>
     * via {@link WebDriverProvider#provide()}.
     */
    public static final class NullDriverProvided extends ShooterException {
        /**
         * Construct a new {@link NullDriverPassed} with the specified detail message.
         */
        public NullDriverProvided() {
            super("The provided WebDriver is null");
        }
    }

    /**
     * Runtime exception when you have passed a null {@link WebDriver}<br>
     * via {@link WebDriverShooter#shoot(WebDriverShooter, ShooterOptions, WebDriver...)}.
     */
    public static final class NullDriverPassed extends ShooterException {
        /**
         * Construct a new {@link NullDriverPassed} with the specified detail message.
         */
        public NullDriverPassed() {
            super("The passed WebDriver is null");
        }
    }
}
