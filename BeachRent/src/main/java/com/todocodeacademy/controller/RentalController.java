package com.todocodeacademy.controller;

import com.todocodeacademy.dto.RentalRequestDTO;
import com.todocodeacademy.dto.RentalResponseDTO;
import com.todocodeacademy.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @PostMapping
    public RentalResponseDTO create(@Valid @RequestBody RentalRequestDTO req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public RentalResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping("/{id}/return")
    public RentalResponseDTO doReturn(@PathVariable Long id) {
        return service.returnRental(id);
    }

    @PostMapping("/{id}/cancel")
    public RentalResponseDTO cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}