package com.todocodeacademy.service;

import com.todocodeacademy.dto.ProductRequestDTO;
import com.todocodeacademy.dto.ProductResponseDTO;
import com.todocodeacademy.model.Product;
import com.todocodeacademy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public ProductResponseDTO create(ProductRequestDTO req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setCategory(req.getCategory());
        product.setStock(req.getStock());
        product.setPricePerHour(req.getPricePerHour());
        product.setActive(req.isActive());
        product = repo.save(product);
        return toResp(product);
    }

    // UPDATE BY ID
    public ProductResponseDTO update(Long id, ProductRequestDTO req) {
        Product product = repo.findById(id).orElse(null);
        if (product == null) return null;
        product.setName(req.getName());
        product.setCategory(req.getCategory());
        product.setStock(req.getStock());
        product.setPricePerHour(req.getPricePerHour());
        product.setActive(req.isActive());
        return toResp(repo.save(product));
    }

    // DELETE BY ID
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // GET BY ID
    public ProductResponseDTO get(Long id) {
        return repo.findById(id).map(this::toResp).orElse(null);
    }

    // GET ALL PRODUCTS
    public List<ProductResponseDTO> list() {
        return repo.findAll().stream().map(this::toResp).collect(Collectors.toList());
    }

    // MAPPING Product → ProductResponseDTO
    private ProductResponseDTO toResp(Product product) {
        ProductResponseDTO prodResp = new ProductResponseDTO();
        prodResp.setId(product.getId());
        prodResp.setName(product.getName());
        prodResp.setCategory(product.getCategory());
        prodResp.setStock(product.getStock());
        prodResp.setPricePerHour(product.getPricePerHour());
        prodResp.setActive(product.isActive());
        return prodResp;
    }
}
