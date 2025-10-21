package bci.exceptions;

/**
 * Exception thrown when a requested user does not exist in the system.
 */
public class UserUnknownException extends Exception {
	public UserUnknownException(String message) {
		super(message);
	}
}
