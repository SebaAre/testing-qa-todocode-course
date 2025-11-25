package com.todocodeacademy.controller;

import com.todocodeacademy.dto.ProductRequestDTO;
import com.todocodeacademy.dto.ProductResponseDTO;
import com.todocodeacademy.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id,
                                     @Valid @RequestBody ProductRequestDTO req) {
        return service.update(id, req);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<ProductResponseDTO> list() {
        return service.list();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
