package org.mikeyo.web.model;

import java.util.Date;

/**
 * Generic interface for sensor readings
 */
public interface SensorReading {
    Date getReadTime();
    String getSensorName();
}
