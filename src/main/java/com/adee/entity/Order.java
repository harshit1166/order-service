package com.adee.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {

    public String customerId;

    public BigDecimal amountUSD;

    public String targetCurrency;

    public BigDecimal convertedAmount;

    public String status;

    public Instant createdAt;
}
