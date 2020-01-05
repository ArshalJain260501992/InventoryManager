package org.assignment.inventorymanager.models.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "Inventory")
public class ProductRecord {

	@Id
	private String id; // Unique identifier of the product.

	private ProductDetails productDetails;

	// This is only used for POC purpose to allow TTL on data in order to respect
	// the limited store on Mongo-Cloud(516MB).
	// It will not be there in an actual scenario.
	@Indexed(expireAfterSeconds = 3600)
	private LocalDateTime createdAt = LocalDateTime.now(); // Time of creation of product

}
