package com.todocodeacademy.service;

import com.todocodeacademy.dto.ProductRequestDTO;
import com.todocodeacademy.dto.ProductResponseDTO;
import com.todocodeacademy.model.Product;
import com.todocodeacademy.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDTO productRequest;

    @BeforeEach
    void setUp() {
        // Setup test data
        product = new Product();
        product.setId(1L);
        product.setName("Beach Umbrella");
        product.setCategory("Sun Protection");
        product.setStock(15);
        product.setPricePerHour(new BigDecimal("8.50"));
        product.setActive(true);

        productRequest = new ProductRequestDTO();
        productRequest.setName("Beach Umbrella");
        productRequest.setCategory("Sun Protection");
        productRequest.setStock(15);
        productRequest.setPricePerHour(new BigDecimal("8.50"));
        productRequest.setActive(true);
    }

    @Test
    void testCreateProduct_Success() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        ProductResponseDTO result = productService.create(productRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Beach Umbrella");
        assertThat(result.getCategory()).isEqualTo("Sun Protection");
        assertThat(result.getStock()).isEqualTo(15);
        assertThat(result.getPricePerHour()).isEqualByComparingTo(new BigDecimal("8.50"));
        assertThat(result.isActive()).isTrue();

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_Success() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        ProductResponseDTO result = productService.update(1L, productRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Beach Umbrella");

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        ProductResponseDTO result = productService.update(999L, productRequest);

        // Then
        assertThat(result).isNull();

        verify(productRepository, times(1)).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testGetProduct_Success() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // When
        ProductResponseDTO result = productService.get(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Beach Umbrella");

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProduct_NotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        ProductResponseDTO result = productService.get(999L);

        // Then
        assertThat(result).isNull();

        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testListProducts_Success() {
        // Given
        Product secondProduct = new Product();
        secondProduct.setId(2L);
        secondProduct.setName("Beach Chair");
        secondProduct.setCategory("Seating");
        secondProduct.setStock(20);
        secondProduct.setPricePerHour(new BigDecimal("6.00"));
        secondProduct.setActive(true);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product, secondProduct));

        // When
        List<ProductResponseDTO> result = productService.list();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Beach Umbrella");
        assertThat(result.get(1).getName()).isEqualTo("Beach Chair");

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct_Success() {
        // Given
        doNothing().when(productRepository).deleteById(1L);

        // When
        productService.delete(1L);

        // Then
        verify(productRepository, times(1)).deleteById(1L);
    }
}