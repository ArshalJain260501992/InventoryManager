package org.assignment.inventorymanager.repos;

import java.util.List;

import org.assignment.inventorymanager.models.domain.ProductRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposotory extends MongoRepository<ProductRecord, String> {

	public List<ProductRecord> findAllByProductDetails_CategoryOrderByProductDetails_AvailabilityDescProductDetails_DiscountedPriceAscId(
			String category);

	public List<ProductRecord> findAllByProductDetails_CategoryAndProductDetails_Availability(String category,
			boolean availability);

	public List<ProductRecord> findAllByOrderById();

}
