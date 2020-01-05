package org.assignment.inventorymanager.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assignment.inventorymanager.exceptions.ProductForUpdateNotFoundExeption;
import org.assignment.inventorymanager.exceptions.ProductNotFoundExeption;
import org.assignment.inventorymanager.models.domain.ProductDetails;
import org.assignment.inventorymanager.models.domain.ProductRecord;
import org.assignment.inventorymanager.models.to.Product;
import org.assignment.inventorymanager.repos.ProductReposotory;
import org.assignment.inventorymanager.services.ProductService;
import org.assignment.inventorymanager.transformers.ProductTransformer;
import org.assignment.inventorymanager.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class PrimaryProductService implements ProductService{

	@Autowired
	private ProductReposotory productRepo;
	
	@Override
	public String add(Product product) {
		ProductValidator.checkProductForValidity(product);
		ProductRecord pr = ProductTransformer.convertTOToDomain(product);
		pr = productRepo.insert(pr);
		return pr.getId();
	}

	
	@Override
	public void update(String id, Product product) {
		Optional<ProductRecord> prOptional = productRepo.findById(id);
		ProductRecord pr = prOptional
				.orElseThrow(() -> new ProductForUpdateNotFoundExeption("Update failed as product does not exist"));
		ProductDetails existing = pr.getProductDetails();
		ProductTransformer.updateProductDetails(product, existing);
		productRepo.save(pr);
	}

	@Override
	public Product getById(String id) {
		Optional<ProductRecord> prOptional = productRepo.findById(id);
		ProductRecord pr = prOptional.orElseThrow(() -> new ProductNotFoundExeption("Product does not exist"));
		return ProductTransformer.convertDomainToTO(pr);
	}

	@Override
	public List<Product> getByCriteria(String category) {
		return productRepo
				.findAllByProductDetails_CategoryOrderByProductDetails_AvailabilityDescProductDetails_DiscountedPriceAscId(
						category)
				.stream().map(ProductTransformer::convertDomainToTO).collect(Collectors.toList());
	}

	@Override
	public List<Product> getByCriteria(String category, boolean availability) {
		return productRepo.findAllByProductDetails_CategoryAndProductDetails_Availability(category, availability)
				.stream().map(ProductTransformer::convertDomainToTO).collect(Collectors.toList());
	}

	@Override
	public List<Product> getAll() {
		return productRepo.findAllByOrderById().stream().map(ProductTransformer::convertDomainToTO)
				.collect(Collectors.toList());
	}

}
