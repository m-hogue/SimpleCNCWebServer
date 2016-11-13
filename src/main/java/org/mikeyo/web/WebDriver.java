package org.mikeyo.web;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.mikeyo.web.resource.TemperatureResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class WebDriver {
    private static final Logger LOG = LoggerFactory.getLogger(WebDriver.class);

    private final Config config;

    public WebDriver() {
        this.config = ConfigFactory.load();
    }

    public void start() {
        // set the port for the web server
        port(this.config.getInt(WebConfig.WEB_PORT.getKey()));
        staticFileLocation("/public");

        // resources
        final TemperatureResource temperatureResource = new TemperatureResource();

        // application
        final CNCWebApplication cncWebApplication = new CNCWebApplication(temperatureResource);
        cncWebApplication.init();

        LOG.info("Starting web server.");
        awaitInitialization();
    }

    public void stop() {
        LOG.info("Stopping web server.");
        Spark.stop();
    }

    public static void main(String[] args) {
        final WebDriver driver = new WebDriver();
        driver.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                driver.stop();
            }
        });
    }
}
