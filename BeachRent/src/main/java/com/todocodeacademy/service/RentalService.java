package com.todocodeacademy.service;

import com.todocodeacademy.dto.RentalRequestDTO;
import com.todocodeacademy.dto.RentalResponseDTO;
import com.todocodeacademy.model.Rental;
import com.todocodeacademy.repository.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {
    private final RentalRepository rentalRepo;

    public RentalService(RentalRepository rentalRepo) {
        this.rentalRepo = rentalRepo;
    }

    // CREATE
    public RentalResponseDTO create(RentalRequestDTO req) {
        Rental rental = new Rental();
        rental.setProductId(req.getProductId());
        rental.setCustomerName(req.getCustomerName());
        rental.setStartTime(req.getStartTime());
        rental.setEndTime(req.getEndTime());
        rental = rentalRepo.save(rental);
        return toResp(rental);
    }

    // GET BY ID
    public RentalResponseDTO get(Long id) {
        return rentalRepo.findById(id).map(this::toResp).orElse(null);
    }

    // SET AS RETURNED
    public RentalResponseDTO returnRental(Long id) {
        return rentalRepo.findById(id).map(rental -> {
            rental.setStatus(Rental.Status.RETURNED);
            return toResp(rentalRepo.save(rental));
        }).orElse(null);
    }

    // CANCEL A RENT
    public RentalResponseDTO cancel(Long id) {
        return rentalRepo.findById(id).map(rental -> {
            rental.setStatus(Rental.Status.CANCELLED);
            return toResp(rentalRepo.save(rental));
        }).orElse(null);
    }

    // MAPPING Rental → RentalResponseDTO
    private RentalResponseDTO toResp(Rental rental) {
        RentalResponseDTO rentalResp = new RentalResponseDTO();
        rentalResp.setId(rental.getId());
        rentalResp.setProductId(rental.getProductId());
        rentalResp.setCustomerName(rental.getCustomerName());
        rentalResp.setStartTime(rental.getStartTime());
        rentalResp.setEndTime(rental.getEndTime());
        rentalResp.setStatus(rental.getStatus().name());
        return rentalResp;
    }
}
