package com.adee.dto;

import java.math.BigDecimal;

public class OrderRequest {

    private String customerId;
    private BigDecimal amountUSD;
    private String targetCurrency;

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
}
