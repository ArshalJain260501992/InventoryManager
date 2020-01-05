package org.assignment.inventorymanager.exceptions;

public class ProductNotFoundExeption extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8484643334373212458L;
	
	public ProductNotFoundExeption(String msg) {
		super(msg);
	}

}
