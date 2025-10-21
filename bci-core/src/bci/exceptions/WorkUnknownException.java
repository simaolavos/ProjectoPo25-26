package bci.exceptions;

/**
 * Exception thrown when a requested work does not exist in the system.
 */
public class WorkUnknownException extends Exception {
	public WorkUnknownException(String message) {
		super(message);
	}
}
