package org.mikeyo.web;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

public class WebDriver {

    private static Config config;

    public static void main(String[] args) {
        config = ConfigFactory.load();

        // set the port for the web server
        port(config.getInt(WebConfig.WEB_PORT.getKey()));
        awaitInitialization();
    }
}
