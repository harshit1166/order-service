package com.adee.service;

import com.adee.client.ExchangeRateClient;
import com.adee.client.ExchangeRateResponse;
import com.adee.dto.OrderRequest;
import com.adee.dto.OrderResponse;
import com.adee.entity.Order;
import com.adee.event.OrderCreatedEvent;
import com.adee.kafka.OrderProducer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@ApplicationScoped
public class OrderService {

    @RestClient
    ExchangeRateClient exchangeRateClient;

    @Inject
    OrderProducer orderProducer;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        ExchangeRateResponse exchangeResponse =
                exchangeRateClient.getRates();

        String targetCurrency =
                request.getTargetCurrency().trim().toUpperCase();

        BigDecimal exchangeRate =
                exchangeResponse.getRates().get(targetCurrency);

        if (exchangeRate == null) {
            throw new IllegalArgumentException(
                    "Unsupported currency: " + targetCurrency
            );
        }

        BigDecimal convertedAmount = request.getAmountUSD()
                .multiply(exchangeRate)
                .setScale(2, RoundingMode.HALF_UP);

        // Create order entity
        Order order = new Order();

        order.customerId = request.getCustomerId();
        order.amountUSD = request.getAmountUSD();
        order.targetCurrency = targetCurrency;
        order.convertedAmount = convertedAmount;
        order.status = "PROCESSED";
        order.createdAt = Instant.now();

        // Save order in PostgreSQL
        order.persist();

        // Create Kafka event
        OrderCreatedEvent event = new OrderCreatedEvent();

        event.orderId = order.id;
        event.customerId = order.customerId;
        event.amountUSD = order.amountUSD;
        event.targetCurrency = order.targetCurrency;
        event.convertedAmount = order.convertedAmount;
        event.status = order.status;
        event.createdAt = order.createdAt;

        // Publish ORDER_CREATED event
        orderProducer.send(event);

        // Create API response
        OrderResponse response = new OrderResponse();

        response.setOrderId(order.id);
        response.setCustomerId(order.customerId);
        response.setAmountUSD(order.amountUSD);
        response.setTargetCurrency(order.targetCurrency);
        response.setConvertedAmount(order.convertedAmount);
        response.setStatus(order.status);
        response.setCreatedAt(order.createdAt);

        return response;
    }
}
