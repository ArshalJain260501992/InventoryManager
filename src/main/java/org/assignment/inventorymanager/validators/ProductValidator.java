package org.assignment.inventorymanager.validators;

import org.assignment.inventorymanager.exceptions.ProductNameEmptyException;
import org.assignment.inventorymanager.models.to.Product;
import org.springframework.util.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductValidator {

	public void checkProductForValidity(Product product) {
		if (StringUtils.isEmpty(product.getName())) {
			throw new ProductNameEmptyException("Product name is empty/null");
		}
	}

}
