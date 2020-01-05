package org.assignment.inventorymanager.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.assignment.inventorymanager.models.to.Product;
import org.assignment.inventorymanager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<Void> add(@RequestBody @Validated Product product, HttpServletRequest request)
			throws URISyntaxException {
		return ResponseEntity.created(new URI(request.getRequestURL().append(productService.add(product)).toString()))
				.build();
	}

	@PutMapping("/{productId}")
	public ResponseEntity<Void> update(@PathVariable(required = true) String productId,
			@RequestBody @Validated Product product) {
		productService.update(productId, product);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{productId}")
	public ResponseEntity<Product> getById(@PathVariable(required = true) String productId) {
		return ResponseEntity.ok(productService.getById(productId));
	}

	@GetMapping()
	public ResponseEntity<List<Product>> getByCriteria(
			@RequestParam(required = false, name = "category") String category,
			@RequestParam(required = false, name = "availability") Boolean availability) {
		ResponseEntity<List<Product>> responseEntity;

		if (category != null) {
			if (availability != null) {
				responseEntity = ResponseEntity.ok(productService.getByCriteria(category, availability));
			} else {
				responseEntity = ResponseEntity.ok(productService.getByCriteria(category));
			}
		} else {
			responseEntity = ResponseEntity.ok(productService.getAll());
		}

		return responseEntity;
	}

}
