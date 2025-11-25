package com.todocodeacademy.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequestDTO {

        @NotBlank(message = "Product name is required")
        @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
        private String name;

        @NotBlank(message = "Category is required")
        private String category;

        @Min(value = 0, message = "Stock cannot be negative")
        private int stock;

        @NotNull(message = "Price per hour is required")
        @DecimalMin(value = "0.01", message = "Price per hour must be greater than 0")
        private BigDecimal pricePerHour;

        private boolean active = true;

        // getters/setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public int getStock() { return stock; }
        public void setStock(int stock) { this.stock = stock; }
        public BigDecimal getPricePerHour() { return pricePerHour; }
        public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }



}
