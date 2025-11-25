package com.todocodeacademy.dto;

import java.math.BigDecimal;

public class ProductRequestDTO {

        private String name;
        private String category;
        private int stock;
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
