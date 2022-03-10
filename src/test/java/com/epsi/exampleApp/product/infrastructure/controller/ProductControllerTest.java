package com.epsi.exampleApp.product.infrastructure.controller;

import com.epsi.exampleApp.product.domain.exceptions.ProductNotFoundException;
import com.epsi.exampleApp.product.domain.model.Product;
import com.epsi.exampleApp.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductRepository repository;

    private List<Product> productsFixture;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        this.productsFixture = Arrays.asList(
                new Product(1, "Test product", 14),
                new Product(2, "Test product 2", 65)
        );
    }

    @Test
    public void contextLoads() {
        assertNotNull(productController);
    }

    @Test
    public void testGetProductWhenItExists() throws Exception {
        when(this.repository.getProduct(anyInt())).thenReturn(this.productsFixture.get(0));
        this.mockMvc
                .perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.label", is("Test product")))
                .andExpect(jsonPath("$.price", is(14)));
        verify(this.repository).getProduct(1);
    }

    @Test
    public void testGetProductWhenItDoesNotExist() throws Exception {
        when(this.repository.getProduct(anyInt())).thenThrow(new ProductNotFoundException("Product not found"));
        this.mockMvc
                .perform(get("/products/6"))
                .andExpect(status().isNotFound());
        verify(this.repository).getProduct(6);
    }

    @Test
    public void testGetProducts() throws Exception {
        when(this.repository.getProducts()).thenReturn(this.productsFixture);

        this.mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].label", is("Test product")))
                .andExpect(jsonPath("$[0].price", is(14)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].label", is("Test product 2")))
                .andExpect(jsonPath("$[1].price", is(65)));
        verify(this.repository).getProducts();
    }
}
