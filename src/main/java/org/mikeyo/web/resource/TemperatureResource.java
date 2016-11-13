package org.mikeyo.web.resource;


import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mikeyo.web.Response;
import org.mikeyo.web.ResponseCreator;
import org.mikeyo.web.model.TemperatureReading;

import java.util.List;

import spark.Request;

/**
 * Class to capture
 */
public class TemperatureResource {
    // TODO: order the temperatures by the readTime
    private List<TemperatureReading> temps = Lists.newArrayList();
    private Gson gson;

    public TemperatureResource() {
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    /**
     * @return the set of temperature readings
     */
    public List<TemperatureReading> getTemperatureReadings() {
        return temps;
    }

    /**
     * Add temperature reading from a sensor
     * @param request the web request
     * @return the sensor reading as understood by the server
     */
    public ResponseCreator addTemperatureReading(final Request request) {

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
        this.temps.add(temperatureReading);
        return Response.created(gson.toJson(temperatureReading));
    }
}
