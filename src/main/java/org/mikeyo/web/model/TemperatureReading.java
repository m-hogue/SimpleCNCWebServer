package org.mikeyo.web.model;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A class to represent a simple temperature reading from a sensor.
 *
 * The temperature units are "as is".
 */
public class TemperatureReading implements SensorReading, Comparable<TemperatureReading> {
    private final String sensorName;
    private final Double tempReading;
    private final Date readTime;


    public TemperatureReading(final String sensorName, final Double tempReading) {
        this.sensorName = checkNotNull(sensorName);
        this.tempReading = checkNotNull(tempReading);
        // read time is assumed to be the time the server receives the reading.
        // We could instead have the arduino tell us what the read time is. Depends on what the read semantics are.
        this.readTime = Date.from(Instant.now());
    }

    /**
     * @return the temperature
     */
    public Double getTempReading() {
        return this.tempReading;
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
                Objects.equals(getTempReading(), that.getTempReading()) &&
                Objects.equals(getReadTime(), that.getReadTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSensorName(), getTempReading(), getReadTime());
    }

    /**
     * The natural ordering of {@link TemperatureReading}s are the ordering of read times.
     *
     * @param that the object to compare to
     * @return the natural comparison between object sensor read times
     */
    @Override
    public int compareTo(final TemperatureReading that) {
        return that.getReadTime().compareTo(getReadTime());
    }
}
