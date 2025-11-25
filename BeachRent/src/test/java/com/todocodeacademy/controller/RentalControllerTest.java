package com.todocodeacademy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todocodeacademy.dto.RentalRequestDTO;
import com.todocodeacademy.dto.RentalResponseDTO;
import com.todocodeacademy.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalController.class)
public class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RentalService rentalService;

    private RentalRequestDTO rentalRequest;
    private RentalResponseDTO rentalResponse;

    @BeforeEach
    void setUp() {
        // Setup request DTO
        rentalRequest = new RentalRequestDTO();
        rentalRequest.setProductId(5L);
        rentalRequest.setCustomerName("John Doe");
        rentalRequest.setStartTime(LocalDateTime.of(2024, 12, 25, 10, 0));
        rentalRequest.setEndTime(LocalDateTime.of(2024, 12, 25, 14, 0));

        // Setup response DTO
        rentalResponse = new RentalResponseDTO();
        rentalResponse.setId(1L);
        rentalResponse.setProductId(5L);
        rentalResponse.setCustomerName("John Doe");
        rentalResponse.setStartTime(LocalDateTime.of(2024, 12, 25, 10, 0));
        rentalResponse.setEndTime(LocalDateTime.of(2024, 12, 25, 14, 0));
        rentalResponse.setStatus("CREATED");
    }

    @Test
    void testCreateRental_Success() throws Exception {
        // Given
        when(rentalService.create(any(RentalRequestDTO.class))).thenReturn(rentalResponse);

        // When & Then
        mockMvc.perform(post("/api/rentals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productId", is(5)))
                .andExpect(jsonPath("$.customerName", is("John Doe")))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.startTime", is("2024-12-25T10:00:00")))
                .andExpect(jsonPath("$.endTime", is("2024-12-25T14:00:00")));

        verify(rentalService, times(1)).create(any(RentalRequestDTO.class));
    }

    @Test
    void testGetRental_Success() throws Exception {
        // Given
        when(rentalService.get(1L)).thenReturn(rentalResponse);

        // When & Then
        mockMvc.perform(get("/api/rentals/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productId", is(5)))
                .andExpect(jsonPath("$.customerName", is("John Doe")))
                .andExpect(jsonPath("$.status", is("CREATED")));

        verify(rentalService, times(1)).get(1L);
    }

    @Test
    void testGetRental_NotFound() throws Exception {
        // Given
        when(rentalService.get(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/rentals/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(rentalService, times(1)).get(999L);
    }

    @Test
    void testReturnRental_Success() throws Exception {
        // Given
        RentalResponseDTO returnedRental = new RentalResponseDTO();
        returnedRental.setId(1L);
        returnedRental.setProductId(5L);
        returnedRental.setCustomerName("John Doe");
        returnedRental.setStartTime(LocalDateTime.of(2024, 12, 25, 10, 0));
        returnedRental.setEndTime(LocalDateTime.of(2024, 12, 25, 14, 0));
        returnedRental.setStatus("RETURNED");

        when(rentalService.returnRental(1L)).thenReturn(returnedRental);

        // When & Then
        mockMvc.perform(post("/api/rentals/1/return")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("RETURNED")))
                .andExpect(jsonPath("$.customerName", is("John Doe")));

        verify(rentalService, times(1)).returnRental(1L);
    }

    @Test
    void testReturnRental_NotFound() throws Exception {
        // Given
        when(rentalService.returnRental(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/api/rentals/999/return")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(rentalService, times(1)).returnRental(999L);
    }

    @Test
    void testCancelRental_Success() throws Exception {
        // Given
        RentalResponseDTO cancelledRental = new RentalResponseDTO();
        cancelledRental.setId(1L);
        cancelledRental.setProductId(5L);
        cancelledRental.setCustomerName("John Doe");
        cancelledRental.setStartTime(LocalDateTime.of(2024, 12, 25, 10, 0));
        cancelledRental.setEndTime(LocalDateTime.of(2024, 12, 25, 14, 0));
        cancelledRental.setStatus("CANCELLED");

        when(rentalService.cancel(1L)).thenReturn(cancelledRental);

        // When & Then
        mockMvc.perform(post("/api/rentals/1/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("CANCELLED")))
                .andExpect(jsonPath("$.customerName", is("John Doe")));

        verify(rentalService, times(1)).cancel(1L);
    }

    @Test
    void testCancelRental_NotFound() throws Exception {
        // Given
        when(rentalService.cancel(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/api/rentals/999/cancel")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(rentalService, times(1)).cancel(999L);
    }
}