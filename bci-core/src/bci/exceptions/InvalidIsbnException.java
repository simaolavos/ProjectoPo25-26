package bci.exceptions;

/**
 * Exception thrown when an ISBN is invalid
 */
public class InvalidIsbnException extends Exception {
	public InvalidIsbnException(String message) {
		super(message);
	}
}
