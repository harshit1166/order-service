package com.adee.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v6/latest/USD")
@RegisterRestClient(configKey = "exchange-rate-api")
@Produces(MediaType.APPLICATION_JSON)
public interface ExchangeRateClient {

    @GET
    ExchangeRateResponse getRates();
}
