package org.assignment.inventorymanager.models.domain;

import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Data;

@Data
public class ProductDetails {

	private String name; // Name of the product.

	@Indexed(background = true)
	private String category = "Misc"; // Category of the product.

	private double retailPrice; // The recommended selling price of the product. The price is given up to two
								// places of decimal.
	
	@Indexed(background = true)
	private double discountedPrice; // The current selling price of the product. The price is given up to two places
									// of decimal.
	
	@Indexed(background = true)
	private boolean availability; // A boolean value that indicates whether the product is in stock (true) or out
									// of stock (false).
	
	@Indexed(background = true)
	private double discountPercentage;
}
