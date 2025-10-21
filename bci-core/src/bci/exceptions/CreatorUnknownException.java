
package bci.exceptions;

/**
 * Exception thrown when a requested creator does not exist in the system.
 */
public class CreatorUnknownException extends Exception {
	public CreatorUnknownException(String message) {
		super(message);
	}
}
