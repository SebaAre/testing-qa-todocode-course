package com.todocodeacademy.service;

import com.todocodeacademy.dto.RentalRequestDTO;
import com.todocodeacademy.dto.RentalResponseDTO;
import com.todocodeacademy.model.Rental;
import com.todocodeacademy.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;

    private Rental rental;
    private RentalRequestDTO rentalRequest;

    @BeforeEach
    void setUp() {
        // Setup test data
        rental = new Rental();
        rental.setId(1L);
        rental.setProductId(5L);
        rental.setCustomerName("Sebastian Arellano");
        rental.setStartTime(LocalDateTime.of(2025, 11, 25, 10, 0));
        rental.setEndTime(LocalDateTime.of(2025, 11, 25, 14, 0));
        rental.setStatus(Rental.Status.CREATED);

        rentalRequest = new RentalRequestDTO();
        rentalRequest.setProductId(5L);
        rentalRequest.setCustomerName("Sebastian Arellano");
        rentalRequest.setStartTime(LocalDateTime.of(2025, 11, 25, 10, 0));
        rentalRequest.setEndTime(LocalDateTime.of(2025, 11, 25, 14, 0));
    }

    @Test
    void testCreateRental_Success() {
        // Given
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        // When
        RentalResponseDTO result = rentalService.create(rentalRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProductId()).isEqualTo(5L);
        assertThat(result.getCustomerName()).isEqualTo("Sebastian Arellano");
        assertThat(result.getStatus()).isEqualTo("CREATED");
        assertThat(result.getStartTime()).isEqualTo(LocalDateTime.of(2025, 11, 25, 10, 0));
        assertThat(result.getEndTime()).isEqualTo(LocalDateTime.of(2025, 11, 25, 14, 0));

        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testGetRental_Success() {
        // Given
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        // When
        RentalResponseDTO result = rentalService.get(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("Sebastian Arellano");
        assertThat(result.getStatus()).isEqualTo("CREATED");

        verify(rentalRepository, times(1)).findById(1L);
    }

    @Test
    void testGetRental_NotFound() {
        // Given
        when(rentalRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        RentalResponseDTO result = rentalService.get(999L);

        // Then
        assertThat(result).isNull();

        verify(rentalRepository, times(1)).findById(999L);
    }

    @Test
    void testReturnRental_Success() {
        // Given
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        Rental returnedRental = new Rental();
        returnedRental.setId(1L);
        returnedRental.setProductId(5L);
        returnedRental.setCustomerName("Sebastian Arellano");
        returnedRental.setStartTime(rental.getStartTime());
        returnedRental.setEndTime(rental.getEndTime());
        returnedRental.setStatus(Rental.Status.RETURNED);

        when(rentalRepository.save(any(Rental.class))).thenReturn(returnedRental);

        // When
        RentalResponseDTO result = rentalService.returnRental(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo("RETURNED");

        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testReturnRental_NotFound() {
        // Given
        when(rentalRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        RentalResponseDTO result = rentalService.returnRental(999L);

        // Then
        assertThat(result).isNull();

        verify(rentalRepository, times(1)).findById(999L);
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testCancelRental_Success() {
        // Given
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        Rental cancelledRental = new Rental();
        cancelledRental.setId(1L);
        cancelledRental.setProductId(5L);
        cancelledRental.setCustomerName("Sebastian Arellano");
        cancelledRental.setStartTime(rental.getStartTime());
        cancelledRental.setEndTime(rental.getEndTime());
        cancelledRental.setStatus(Rental.Status.CANCELLED);

        when(rentalRepository.save(any(Rental.class))).thenReturn(cancelledRental);

        // When
        RentalResponseDTO result = rentalService.cancel(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo("CANCELLED");

        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testCancelRental_NotFound() {
        // Given
        when(rentalRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        RentalResponseDTO result = rentalService.cancel(999L);

        // Then
        assertThat(result).isNull();

        verify(rentalRepository, times(1)).findById(999L);
        verify(rentalRepository, never()).save(any(Rental.class));
    }
}
