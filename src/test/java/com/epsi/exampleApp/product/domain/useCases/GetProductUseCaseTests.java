package com.epsi.exampleApp.product.domain.useCases;

import com.epsi.exampleApp.product.domain.exceptions.ProductNotFoundException;
import com.epsi.exampleApp.product.domain.model.Product;
import com.epsi.exampleApp.product.domain.repository.ProductRepository;
import com.epsi.exampleApp.product.infrastructure.repository.ProductMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetProductUseCaseTests {
    private GetProductUseCase getProductUseCase;
    private ProductRepository productRepository;
    private Product productFixture;

    @BeforeEach
    public void setUp() {
        this.productRepository = mock(ProductMemoryRepository.class);
        this.getProductUseCase = new GetProductUseCase(this.productRepository);

        this.productFixture = new Product(1, "Test product", 2);
    }

    @Test
    public void testProductExists() throws ProductNotFoundException {
        when(this.productRepository.getProduct(anyInt())).thenReturn(this.productFixture);

        Product product = this.getProductUseCase.execute(1);

        assertEquals(product, this.productFixture);
        verify(this.productRepository).getProduct(1);
    }

    @Test()
    public void testProductDoesNotExist() throws ProductNotFoundException {
        when(this.productRepository.getProduct(anyInt())).thenThrow(new ProductNotFoundException("Product not found"));

        assertThrows(ProductNotFoundException.class, () -> {
            this.getProductUseCase.execute(4);
        });
        verify(this.productRepository).getProduct(4);
    }
}
