package org.assignment.inventorymanager.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.assignment.inventorymanager.exceptions.ProductForUpdateNotFoundExeption;
import org.assignment.inventorymanager.exceptions.ProductNameEmptyException;
import org.assignment.inventorymanager.exceptions.ProductNotFoundExeption;
import org.assignment.inventorymanager.models.domain.ProductDetails;
import org.assignment.inventorymanager.models.domain.ProductRecord;
import org.assignment.inventorymanager.models.to.Product;
import org.assignment.inventorymanager.repos.ProductReposotory;
import org.assignment.inventorymanager.services.impl.PrimaryProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PrimaryProductServiceTest {

	@MockBean
	private ProductReposotory productRepo;

	@InjectMocks
	private PrimaryProductService primaryProductService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddSuccess() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		doReturn(pr).when(productRepo).insert(any(ProductRecord.class));
		Product product = new Product();
		product.setName("Test");
		assertEquals("1234", primaryProductService.add(product));
	}

	@Test(expected = ProductNameEmptyException.class)
	public void testAddNameMising() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		doReturn(pr).when(productRepo).insert(any(ProductRecord.class));
		Product product = new Product();
		primaryProductService.add(product);
	}

	@Test
	public void testUpdateSuccess() {
		Product product = new Product();
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		pr.setProductDetails(new ProductDetails());
		Optional<ProductRecord> o = Optional.of(pr);
		doReturn(o).when(productRepo).findById(any(String.class));

		doReturn(pr).when(productRepo).save(any(ProductRecord.class));
		primaryProductService.update("1234", product);

	}

	@Test(expected = ProductForUpdateNotFoundExeption.class)
	public void testUpdateProductNotFound() {
		Product product = new Product();
		product.setId("1234");
		Optional<Product> o = Optional.empty();
		doReturn(o).when(productRepo).findById(any(String.class));
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		doReturn(pr).when(productRepo).save(any(ProductRecord.class));
		primaryProductService.update("1234", product);
	}

	@Test
	public void testGetByIdSuccess() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		pr.setProductDetails(new ProductDetails());
		Optional<ProductRecord> o = Optional.of(pr);
		doReturn(o).when(productRepo).findById(any(String.class));
		assertEquals("1234", primaryProductService.getById("1234").getId());
	}

	@Test(expected = ProductNotFoundExeption.class)
	public void testGetByIdFailure() {
		Optional<Product> o = Optional.empty();
		doReturn(o).when(productRepo).findById(any(String.class));
		assertEquals("1234", primaryProductService.getById("1234").getId());
	}

	@Test
	public void testGetByCriteria() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		pr.setProductDetails(new ProductDetails());
		ProductRecord[] arr = { pr };
		doReturn(new ArrayList<>(Arrays.asList(arr))).when(productRepo)
				.findAllByProductDetails_CategoryOrderByProductDetails_AvailabilityDescProductDetails_DiscountedPriceAscId(
						any(String.class));
		assertEquals("1234", primaryProductService.getByCriteria("misc").get(0).getId());
	}

	@Test
	public void testGetByCriteriaAndAvailability() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		pr.setProductDetails(new ProductDetails());
		ProductRecord[] arr = { pr };
		doReturn(new ArrayList<>(Arrays.asList(arr))).when(productRepo)
				.findAllByProductDetails_CategoryAndProductDetails_Availability("misc", true);
		assertEquals("1234", primaryProductService.getByCriteria("misc", true).get(0).getId());
	}
	
	@Test
	public void testGetAll() {
		ProductRecord pr = new ProductRecord();
		pr.setId("1234");
		pr.setProductDetails(new ProductDetails());
		ProductRecord[] arr = { pr };
		doReturn(new ArrayList<>(Arrays.asList(arr))).when(productRepo)
				.findAllByOrderById();
		assertEquals("1234", primaryProductService.getAll().get(0).getId());
	}

}
