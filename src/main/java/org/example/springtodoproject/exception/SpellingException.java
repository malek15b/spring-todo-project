package org.example.springtodoproject.exception;

public class SpellingException extends Exception {
    public SpellingException(String message) {
        super("The text is incorrect: " + message);
    }
}
