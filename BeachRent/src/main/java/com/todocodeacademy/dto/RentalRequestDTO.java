package com.todocodeacademy.dto;
import java.time.LocalDateTime;

public class RentalRequestDTO {

    private Long productId;
    private String customerName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // getters/setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

}
