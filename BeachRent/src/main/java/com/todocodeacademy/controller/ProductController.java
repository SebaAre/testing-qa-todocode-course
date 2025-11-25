package com.todocodeacademy.controller;

import com.todocodeacademy.dto.ProductRequestDTO;
import com.todocodeacademy.dto.ProductResponseDTO;
import com.todocodeacademy.service.ProductService;
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

    // Endpoint para crear un producto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@RequestBody ProductRequestDTO req) {
        return service.create(req);
    }

    // Endpoint para actualizar un producto por id
    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @RequestBody ProductRequestDTO req) {
        return service.update(id, req);
    }

    // Endpoint para obtener un producto por id
    @GetMapping("/{id}")
    public ProductResponseDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    // Endpoint para listar todos los productos
    @GetMapping
    public List<ProductResponseDTO> list() {
        return service.list();
    }

    // Endpoint para eliminar un producto por id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
