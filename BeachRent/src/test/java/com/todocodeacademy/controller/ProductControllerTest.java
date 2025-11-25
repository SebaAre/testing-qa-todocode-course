package com.todocodeacademy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocodeacademy.dto.ProductRequestDTO;
import com.todocodeacademy.dto.ProductResponseDTO;
import com.todocodeacademy.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    private ProductRequestDTO productRequest;
    private ProductResponseDTO productResponse;

    @BeforeEach
    void setUp() {
        // Setup request DTO
        productRequest = new ProductRequestDTO();
        productRequest.setName("Beach Umbrella");
        productRequest.setCategory("Sun Protection");
        productRequest.setStock(15);
        productRequest.setPricePerHour(new BigDecimal("8.50"));
        productRequest.setActive(true);

        // Setup response DTO
        productResponse = new ProductResponseDTO();
        productResponse.setId(1L);
        productResponse.setName("Beach Umbrella");
        productResponse.setCategory("Sun Protection");
        productResponse.setStock(15);
        productResponse.setPricePerHour(new BigDecimal("8.50"));
        productResponse.setActive(true);
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        // Given
        when(productService.create(any(ProductRequestDTO.class))).thenReturn(productResponse);

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Beach Umbrella")))
                .andExpect(jsonPath("$.category", is("Sun Protection")))
                .andExpect(jsonPath("$.stock", is(15)))
                .andExpect(jsonPath("$.pricePerHour", is(8.50)))
                .andExpect(jsonPath("$.active", is(true)));

        verify(productService, times(1)).create(any(ProductRequestDTO.class));
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        // Given
        when(productService.update(eq(1L), any(ProductRequestDTO.class))).thenReturn(productResponse);

        // When & Then
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Beach Umbrella")))
                .andExpect(jsonPath("$.stock", is(15)));

        verify(productService, times(1)).update(eq(1L), any(ProductRequestDTO.class));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        // Given
        when(productService.update(eq(999L), any(ProductRequestDTO.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(put("/api/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(productService, times(1)).update(eq(999L), any(ProductRequestDTO.class));
    }

    @Test
    void testGetProduct_Success() throws Exception {
        // Given
        when(productService.get(1L)).thenReturn(productResponse);

        // When & Then
        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Beach Umbrella")))
                .andExpect(jsonPath("$.category", is("Sun Protection")))
                .andExpect(jsonPath("$.stock", is(15)));

        verify(productService, times(1)).get(1L);
    }

    @Test
    void testGetProduct_NotFound() throws Exception {
        // Given
        when(productService.get(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(productService, times(1)).get(999L);
    }

    @Test
    void testListProducts_Success() throws Exception {
        // Given
        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(2L);
        product2.setName("Beach Chair");
        product2.setCategory("Seating");
        product2.setStock(20);
        product2.setPricePerHour(new BigDecimal("6.00"));
        product2.setActive(true);

        List<ProductResponseDTO> products = Arrays.asList(productResponse, product2);
        when(productService.list()).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Beach Umbrella")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Beach Chair")));

        verify(productService, times(1)).list();
    }

    @Test
    void testListProducts_EmptyList() throws Exception {
        // Given
        when(productService.list()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(productService, times(1)).list();
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        // Given
        doNothing().when(productService).delete(1L);

        // When & Then
        mockMvc.perform(delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(1L);
    }
}