package com.leovegas.wallet.exception;

/**
 * This is an Exception to handle the error when there is no entity available for the search query
 *
 */
public class EntityNotFoundException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public EntityNotFoundException(String message) {
		super(message);
	}

}
