package org.mikeyo.web.resource;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TemperatureResourceTest extends ResourceBase {

    @Test
    public void testAddTemperatureReading() {
        given()
                .when()
                    .post("/temperature/sensor1?tempReading=45.5")
                .then()
                    .statusCode(201)
                    .body("sensorName", equalTo("sensor1"))
                    .body("tempReading", equalTo("45.5"))
                    .body("readTime", notNullValue());


    }
}
