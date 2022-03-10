package com.epsi.exampleApp.product.domain.useCases;

import com.epsi.exampleApp.product.domain.model.Product;
import com.epsi.exampleApp.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetProductsUseCaseTests {
    private GetProductsUseCase getProductsUseCase;
    private ProductRepository productRepository;

    private Product productFixture1;
    private Product productFixture2;


    @BeforeEach
    public void setUp() {
        this.productRepository = mock(ProductRepository.class);
        this.getProductsUseCase = new GetProductsUseCase(productRepository);

        this.productFixture1 = new Product(1, "Test product 1", 14);
        this.productFixture2 = new Product(2, "Test product 2", 15);
    }

    @Test
    public void testMultipleProducts() {


        when(this.productRepository.getProducts()).thenReturn(Arrays.asList(this.productFixture1, this.productFixture2));

        List<Product> products = this.getProductsUseCase.execute();

        assertEquals(products, Arrays.asList(this.productFixture1, this.productFixture2));
        verify(this.productRepository).getProducts();
    }

}
