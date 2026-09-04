package com.adee.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class OrderResponse {

    private Long orderId;
    private String customerId;
    private BigDecimal amountUSD;
    private String targetCurrency;
    private BigDecimal convertedAmount;
    private String status;
    private Instant createdAt;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmountUSD() {
        return amountUSD;
    }

    public void setAmountUSD(BigDecimal amountUSD) {
        this.amountUSD = amountUSD;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
