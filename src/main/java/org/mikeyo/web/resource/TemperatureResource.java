package org.mikeyo.web.resource;


//import org.eclipse.jetty.server.Response;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.mikeyo.web.Response;
import org.mikeyo.web.ResponseCreator;
import org.mikeyo.web.model.TemperatureReading;

import java.util.Collections;
import java.util.List;

/**
 * Class to capture
 */
public class TemperatureResource {
    // TODO: order the temperatures by the readTime
    private List<TemperatureReading> temps = Collections.emptyList();
    private Gson gson;

    public TemperatureResource() {
        this.gson = new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Add temperature reading from a sensor
     * @param sensorName the name of the sensor
     * @param tempReading the temperature reading in fahrenheit
     * @return a web response
     */
    public ResponseCreator addTemperatureReading(final String sensorName, final Double tempReading) {
        if(Strings.isNullOrEmpty(sensorName)) {
            return Response.badRequest(gson.toJson("sensor name is null or empty."));
        }
        if(tempReading == null) {
            return Response.badRequest(gson.toJson("Invalid null temperature reading."));
        }

        this.temps.add(new TemperatureReading(sensorName, tempReading));
        return Response.created(gson.toJson("Successfully posted temperature reading."));
    }

    /**
     * @return the set of temperature readings
     */
    public List<TemperatureReading> getTemperatureReadings() {
        return temps;
    }
}
