package org.mikeyo.web.resource;


import com.google.common.base.Strings;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mikeyo.web.Response;
import org.mikeyo.web.ResponseCreator;
import org.mikeyo.web.model.TemperatureReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;

/**
 * web resource for recording/retrieving temperature sensor readings
 *
 * readings are recorded in memory and are not persisted anywhere. That meand that recordings are kept
 * as long as the web server is alive.
 */
public class TemperatureResource {
    private static final Logger LOG = LoggerFactory.getLogger(TemperatureResource.class);
    private SetMultimap<String, TemperatureReading> temperatureReadingsBySensor;
    private Gson gson;

    public TemperatureResource() {
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
        // want to maintain natural ordering of values for each sensor
        this.temperatureReadingsBySensor = MultimapBuilder.SetMultimapBuilder
                .hashKeys()
                .treeSetValues()
                .build();
    }

    /**
     * @return all sensor readings to date
     */
    public ResponseCreator getAllTemperatureReadings() {
        LOG.info("Get all temp readings request.");
        return Response.ok(gson.toJson(this.temperatureReadingsBySensor.values()));
    }

    /**
     * @return the set of temperature readings
     */
    public ResponseCreator getTemperatureReadings(final Request request) {
        LOG.info("Get all temp readings request {}.", request.body());
        final String sensorName = request.params("sensorName");
        if(Strings.isNullOrEmpty(sensorName)) {
            return Response.badRequest(gson.toJson("sensor name is null or empty."));
        }

        return Response.ok(gson.toJson(this.temperatureReadingsBySensor.get(sensorName)));
    }

    /**
     * Add temperature reading from a sensor
     * @param request the web request
     * @return the sensor reading as understood by the server
     */
    public ResponseCreator addTemperatureReading(final Request request) {
        LOG.info("Get all temp readings for sensor request {}.", request.body());
        final String sensorName = request.params("sensorName");

        final Double tempReading;
        try {
            tempReading = Double.valueOf(request.queryParams("tempReading"));
        } catch(final NumberFormatException e) {
            return Response.badRequest(gson.toJson("Invalid temperature reading: " + request.queryParams("tempReading")));
        }

        if(Strings.isNullOrEmpty(sensorName)) {
            return Response.badRequest(gson.toJson("sensor name is null or empty."));
        }

        final TemperatureReading temperatureReading = new TemperatureReading(sensorName, tempReading);
        boolean added = this.temperatureReadingsBySensor.put(temperatureReading.getSensorName(), temperatureReading);

        if(added) {
            return Response.created(gson.toJson(temperatureReading));
        } else {
            return Response.noContent();
        }
    }
}
