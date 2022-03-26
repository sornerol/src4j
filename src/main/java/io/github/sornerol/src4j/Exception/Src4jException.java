package io.github.sornerol.src4j.Exception;

/**
 * This exception is thrown whenever an API request returns a non-success return code, or when data
 * fails validation.
 */
public class Src4jException extends Exception {
    public Src4jException(String message) {
        super(message);
    }
}
