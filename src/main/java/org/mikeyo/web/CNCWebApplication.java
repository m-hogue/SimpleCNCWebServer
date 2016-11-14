package org.mikeyo.web;

import org.mikeyo.web.resource.TemperatureResource;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static com.google.common.base.Preconditions.checkNotNull;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * The primary location for initializing all web resources
 */
public class CNCWebApplication implements SparkApplication {
    private final TemperatureResource temperatureResource;

    CNCWebApplication(final TemperatureResource temperatureResource) {
        this.temperatureResource = checkNotNull(temperatureResource);
    }

    @Override
    public void init() {
        // add a temp reading
        post("/temperature/:sensorName", map((req, res) -> this.temperatureResource.addTemperatureReading(req)));
        // get temp readings for a sensor
        get("/temperature/:sensorName", map((req, res) -> this.temperatureResource.getTemperatureReadings(req)));
        // get all temp readings
        get("/temperature", map((req, res) -> this.temperatureResource.getAllTemperatureReadings(req)));
    }

    private Route map(final Converter converter) {
        return (req, res) -> converter.convert(req, res).handle(req,res);
    }

    /**
     * Simple converter interface to convert a (request, response) pair.
     */
    @FunctionalInterface
    private interface Converter {
        ResponseCreator convert(Request req, Response res);
    }
}
