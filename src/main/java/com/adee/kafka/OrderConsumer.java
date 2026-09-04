package com.adee.kafka;

import com.adee.event.OrderCreatedEvent;
import com.adee.opensearch.OpenSearchClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderConsumer {

    @Inject
    OpenSearchClient openSearchClient;

    @Incoming("order-consumer")
    public void consume(OrderCreatedEvent event) {

        System.out.println(
                "Received ORDER_CREATED event: " + event.orderId
        );

        openSearchClient.indexOrder(event);
    }
}
