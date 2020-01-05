package org.assignment.inventorymanager.models.to;

import org.assignment.inventorymanager.validators.RetailPriceConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Product {

	@JsonProperty(value = "id", required = false)
	private String id; // Unique identifier of the product.

	@JsonProperty(value = "name" , required = false)
	private String name; // Name of the product.

	@JsonProperty(value = "category", required = false)
	private String category; // Category of the product.

	@JsonProperty(value = "retail_price")
	@RetailPriceConstraint
	private double retailPrice; // The recommended selling price of the product. The price is given up to two
								// places of decimal.

	@JsonProperty(value = "discounted_price")
	private double discountedPrice; // The current selling price of the product. The price is given up to two places
									// of decimal.

	@JsonProperty(value = "availability")
	private boolean availability; // A boolean value that indicates whether the product is in stock (true) or out
									// of stock (false).

}
