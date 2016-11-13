package org.mikeyo.web.resource;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TemperatureResourceTest extends ResourceBase {

    @Test
    public void testAddAndGetTemperatureReading() {
        given()
                .when()
                    .post("/temperature/sensor1?tempReading=45.5")
                .then()
                    .statusCode(201)
                    .body("sensorName", equalTo("sensor1"))
                    .body("tempReading", is(45.5f))
                    .body("readTime", notNullValue());

        given()
                .when()
                .post("/temperature/sensor2?tempReading=-30")
                .then()
                .statusCode(201)
                .body("sensorName", equalTo("sensor2"))
                .body("tempReading", is(-30f))
                .body("readTime", notNullValue());

        given()
                .when()
                    .get("/temperature/sensor1")
                .then()
                    .statusCode(200)
                .body("size()", is(1))
                .body("[0].sensorName", equalTo("sensor1"))
                .body("[0].tempReading", is(45.5f))
                .body("[0].readTime", notNullValue());

        given()
                .when()
                .get("/temperature/sensor2")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].sensorName", equalTo("sensor2"))
                .body("[0].tempReading", is(-30f))
                .body("[0].readTime", notNullValue());

        given()
                .when()
                .get("/temperature")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].sensorName", equalTo("sensor2"))
                .body("[0].tempReading", is(-30f))
                .body("[0].readTime", notNullValue())
                .body("[1].sensorName", equalTo("sensor1"))
                .body("[1].tempReading", is(45.5f))
                .body("[1].readTime", notNullValue());
    }
}
