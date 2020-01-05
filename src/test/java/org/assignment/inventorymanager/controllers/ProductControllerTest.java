package org.assignment.inventorymanager.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.assignment.inventorymanager.exceptions.ProductForUpdateNotFoundExeption;
import org.assignment.inventorymanager.exceptions.ProductNameEmptyException;
import org.assignment.inventorymanager.exceptions.ProductNotFoundExeption;
import org.assignment.inventorymanager.models.to.Product;
import org.assignment.inventorymanager.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testAddSuccess() throws Exception {
		doReturn(null).when(productService).add(any());
		this.mockMvc
				.perform(post("/products")
						.content("{\r\n" + "        \"name\": \"Mac\",\r\n" + "        \"category\": \"Furniture\",\r\n"
								+ "        \"retail_price\": 55,\r\n" + "        \"discounted_price\": 50,\r\n"
								+ "        \"availability\": true\r\n" + "    }")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}

	@Test
	public void testAddWithEmptyName() throws Exception {
		doThrow(ProductNameEmptyException.class).when(productService).add(any());
		this.mockMvc
				.perform(post("/products")
						.content("{\r\n" + "        \"name\": null,\r\n" + "        \"category\": \"Furniture\",\r\n"
								+ "        \"retail_price\": 55,\r\n" + "        \"discounted_price\": 50,\r\n"
								+ "        \"availability\": true\r\n" + "    }")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAddWithDuplicateId() throws Exception {
		doThrow(DuplicateKeyException.class).when(productService).add(any());
		this.mockMvc
				.perform(post("/products")
						.content("{\r\n" + "        \"name\": \"Mac\",\r\n" + "        \"category\": \"Furniture\",\r\n"
								+ "        \"retail_price\": 55,\r\n" + "        \"discounted_price\": 50,\r\n"
								+ "        \"availability\": true\r\n" + "    }")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateSuccess() throws Exception {
		doNothing().when(productService).update(any(), any());
		this.mockMvc
				.perform(put("/products/{id}", "1234")
						.content("{\r\n" + "        \"name\": \"Mac\",\r\n" + "        \"category\": \"Furniture\",\r\n"
								+ "        \"retail_price\": 55,\r\n" + "        \"discounted_price\": 50,\r\n"
								+ "        \"availability\": true\r\n" + "    }")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateProductNotFound() throws Exception {
		doThrow(ProductForUpdateNotFoundExeption.class).when(productService).update(any(), any());
		this.mockMvc
				.perform(put("/products/{id}", "1234")
						.content("{\r\n" + "        \"name\": \"Mac\",\r\n" + "        \"category\": \"Furniture\",\r\n"
								+ "        \"retail_price\": 55,\r\n" + "        \"discounted_price\": 50,\r\n"
								+ "        \"availability\": true\r\n" + "    }")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testGetByIdSuccess() throws Exception {
		doReturn(new Product()).when(productService).getById(any());
		this.mockMvc.perform(get("/products/{id}", "1234")).andExpect(status().isOk());
	}

	@Test
	public void testGetByIdProductNotFound() throws Exception {
		doThrow(ProductNotFoundExeption.class).when(productService).getById(any());
		this.mockMvc.perform(get("/products/{id}", "1234")).andExpect(status().isNotFound());
	}

	@Test
	public void testGetByCategorySuccess() throws Exception {
		Product product = new Product();
		product.setId("1234");
		doReturn(Arrays.asList(product)).when(productService).getByCriteria(any());
		this.mockMvc.perform(get("/products").queryParam("category", "misc")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0]id").value("1234"));
	}

	@Test
	public void testGetByCategoryAndAvailabilitySuccess() throws Exception {
		Product product = new Product();
		product.setId("1234");
		doReturn(Arrays.asList(product)).when(productService).getByCriteria("misc", true);
		this.mockMvc.perform(get("/products").queryParam("category", "misc").queryParam("availability", "1"))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$[0]id").value("1234"));
	}

	@Test
	public void testGetByAllSuccess() throws Exception {
		Product product = new Product();
		product.setId("1234");
		doReturn(Arrays.asList(product)).when(productService).getAll();
		this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0]id").value("1234"));
	}
}
