package com.adee.kafka;

import com.adee.event.OrderCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderProducer {

    @Channel("order-created")
    Emitter<OrderCreatedEvent> emitter;

    public CompletionStage<Void> send(OrderCreatedEvent event) {
        return emitter.send(event);
    }
}
