package org.purl.co.exceptions;

/**
 * The expression thrown when trying to create a new collection/item with an URI id that is
 * already used within the creator environment.
 * 
 * @author Paolo Ciccarese
 * @author Silvio Peroni
 *
 */
@SuppressWarnings("serial")
public class ExistingCOEntityException extends Exception {

	/**
	 * It constructs a new exception with null as its detail message. 
	 */
	public ExistingCOEntityException() {}

	/**
	 * It constructs a new exception with the specified detail message.
	 * @param message the detail message.
	 */
	public ExistingCOEntityException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of 
	 * {@code (cause==null ? null : cause.toString())} (which typically contains the class 
	 * and detail message of cause).
	 * @param cause the cause. A {@code null} value is permitted, and indicates that 
	 * the cause is nonexistent or unknown.)
	 */
	public ExistingCOEntityException(Throwable cause) {
		super(cause);
	}

	/**
	 * It constructs a new exception with the specified detail message and cause.
	 * @param message the detail message.
	 * @param cause the cause. A {@code null} value is permitted, and indicates that 
	 * the cause is nonexistent or unknown.)
	 */
	public ExistingCOEntityException(String message, Throwable cause) {
		super(message, cause);
	}

}
