package com.seriousplay.productitity.jdbc.exceptions;

import org.springframework.dao.NonTransientDataAccessException;

public class TooManyResultsException extends NonTransientDataAccessException {
    /**
     * Constructor for NonTransientDataAccessException.
     *
     * @param msg the detail message
     */
    public TooManyResultsException(String msg) {
        super(msg);
    }

    /**
     * Constructor for NonTransientDataAccessException.
     *
     * @param msg   the detail message
     * @param cause the root cause (usually from using a underlying
     */
    public TooManyResultsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
