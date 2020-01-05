package org.assignment.inventorymanager.transformers;

import org.assignment.inventorymanager.models.domain.ProductDetails;
import org.assignment.inventorymanager.models.domain.ProductRecord;
import org.assignment.inventorymanager.models.to.Product;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductTransformer {

	public static void updateProductDetails(Product src, ProductDetails existing) {
		existing.setAvailability(src.isAvailability());
		existing.setRetailPrice(src.getRetailPrice());
		existing.setDiscountedPrice(src.getRetailPrice());
	}

	public ProductRecord convertTOToDomain(Product product) {
		ProductRecord pr = new ProductRecord();
		ProductDetails pd = new ProductDetails();
		pr.setProductDetails(pd);
		pr.setId(product.getId());
		pd.setAvailability(product.isAvailability());
		pd.setCategory(product.getCategory());
		pd.setDiscountedPrice(product.getDiscountedPrice());
		pd.setRetailPrice(product.getRetailPrice());
		pd.setName(product.getName());
		double discPrctg = ((product.getRetailPrice() - product.getDiscountedPrice())/product.getRetailPrice())*100;
		pd.setDiscountPercentage(discPrctg);
		return pr;
	}

	public Product convertDomainToTO(ProductRecord pr) {
		ProductDetails pd = pr.getProductDetails();
		Product product = new Product();
		product.setId(pr.getId());
		product.setAvailability(pd.isAvailability());
		product.setCategory(pd.getCategory());
		product.setDiscountedPrice(pd.getDiscountedPrice());
		product.setRetailPrice(pd.getRetailPrice());
		product.setName(pd.getName());
		return product;
	}
}
