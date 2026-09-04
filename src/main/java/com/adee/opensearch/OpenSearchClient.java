package com.adee.opensearch;

import com.adee.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class OpenSearchClient {

    @ConfigProperty(name = "opensearch.url")
    String openSearchUrl;

    @Inject
    ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void indexOrder(OrderCreatedEvent event) {

        try {
            String json = objectMapper.writeValueAsString(event);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(
                            openSearchUrl + "/orders/_doc/" + event.orderId
                    ))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() < 200 ||
                response.statusCode() >= 300) {

                throw new RuntimeException(
                        "OpenSearch indexing failed. HTTP status: "
                                + response.statusCode()
                                + ", response: "
                                + response.body()
                );
            }

            System.out.println(
                    "Order indexed in OpenSearch: " + event.orderId
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to index order in OpenSearch",
                    e
            );
        }
    }
}
