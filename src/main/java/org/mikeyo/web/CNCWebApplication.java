package org.mikeyo.web;

import org.mikeyo.web.resource.TemperatureResource;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static com.google.common.base.Preconditions.checkNotNull;
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
        post("/temperature/:sensorName", map((req, res) -> {
            final String sensorName = req.params("sensorName");
            final Double tempReading = req.attribute("temperature");

            return this.temperatureResource.addTemperatureReading(sensorName, tempReading);
        }));
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
