package com.alg.productmanager.exceptions;

/** Exception to be thrown when a product does not exist. */
public class ProductDoesNotExistException extends Exception {

	public Long id;

	/** Constructor. */
	public ProductDoesNotExistException(GenericError error, Long id) {
		super(error.getDescription() + id);
		this.id = id;
	}
}
