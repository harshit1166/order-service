package com.adee;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class OrderResourceTest {

    @Test
    void testCreateOrder() {

        given()
            .contentType("application/json")
            .body("""
                {
                  "customerId": "CUST-1001",
                  "amountUSD": 150.00,
                  "targetCurrency": "EUR"
                }
                """)
        .when()
            .post("/api/v1/orders")
        .then()
            .statusCode(201);
    }
}
