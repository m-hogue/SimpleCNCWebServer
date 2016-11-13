package org.mikeyo.web.model;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A class to represent a simple temperature reading from a sensor
 */
public class TemperatureReading implements SensorReading {
    private final String sensorName;
    private final Double tempInFahrenheit;
    private final Date readTime;


    public TemperatureReading(final String sensorName, final Double tempInFahrenheit) {
        this.sensorName = checkNotNull(sensorName);
        this.tempInFahrenheit = checkNotNull(tempInFahrenheit);
        // read time is assumed to be the time the server receives the reading.
        this.readTime = Date.from(Instant.now());
    }

    /**
     * @return the temperature in fahrenheit
     */
    public Double getTempInFahrenheit() {
        return this.tempInFahrenheit;
    }

    /**
     * @return the time the web server received the sensor reading
     */
    @Override
    public Date getReadTime() {
        return this.readTime;
    }

    /**
     * @return the name of the sensor that this temperature came from
     */
    @Override
    public String getSensorName() {
        return this.sensorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemperatureReading that = (TemperatureReading) o;
        return Objects.equals(getSensorName(), that.getSensorName()) &&
                Objects.equals(getTempInFahrenheit(), that.getTempInFahrenheit()) &&
                Objects.equals(getReadTime(), that.getReadTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSensorName(), getTempInFahrenheit(), getReadTime());
    }
}
