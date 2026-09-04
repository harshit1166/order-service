package com.adee.kafka;

import com.adee.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class OrderCreatedEventDeserializer
        implements Deserializer<OrderCreatedEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper()
        .findAndRegisterModules();

    @Override
    public OrderCreatedEvent deserialize(
            String topic,
            byte[] data) {

        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(
                    data,
                    OrderCreatedEvent.class
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to deserialize OrderCreatedEvent",
                    e
            );
        }
    }
}
