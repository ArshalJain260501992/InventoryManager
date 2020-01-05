package org.assignment.inventorymanager.services;

import java.util.List;

import org.assignment.inventorymanager.models.to.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

	public String add(Product product);

	public void update(String productId, Product product);

	public Product getById(String id);

	public List<Product> getByCriteria(String category);

	public List<Product> getByCriteria(String category, boolean availability);
	
	public List<Product> getAll();

}
