package org.assignment.inventorymanager.controllers.advice;

import org.assignment.inventorymanager.exceptions.ProductForUpdateNotFoundExeption;
import org.assignment.inventorymanager.exceptions.ProductNameEmptyException;
import org.assignment.inventorymanager.exceptions.ProductNotFoundExeption;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "org.assignment")
public class ProductControllerAdvice {

	@ExceptionHandler(value = { DuplicateKeyException.class, ProductNameEmptyException.class,
			ProductForUpdateNotFoundExeption.class, MethodArgumentNotValidException.class })
	public ResponseEntity<Void> handleDuplicateKeyExcp(Exception e) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(ProductNotFoundExeption.class)
	public ResponseEntity<Void> handleProductNotFoundExcp(Exception e) {
		return ResponseEntity.notFound().build();

	}
}
