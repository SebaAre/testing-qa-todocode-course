package com.todocodeacademy.controller;

import com.todocodeacademy.dto.RentalRequestDTO;
import com.todocodeacademy.dto.RentalResponseDTO;
import com.todocodeacademy.service.RentalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    // Endpoint para crear un alquiler
    @PostMapping
    public RentalResponseDTO create(@RequestBody RentalRequestDTO req) {
        return service.create(req);
    }

    // Endpoint para obtener un alquiler por id
    @GetMapping("/{id}")
    public RentalResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    // Endpoint para devolver un alquiler
    @PostMapping("/{id}/return")
    public RentalResponseDTO doReturn(@PathVariable Long id) {
        return service.returnRental(id);
    }

    // Endpoint para cancelar un alquiler
    @PostMapping("/{id}/cancel")
    public RentalResponseDTO cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
