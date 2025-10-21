package bci.exceptions;

/**
 * Exception thrown when a user registration attempt fails.
 */
public class UserRegistrationFailedException extends Exception {
	public UserRegistrationFailedException(String message) {
		super(message);
	}
}
