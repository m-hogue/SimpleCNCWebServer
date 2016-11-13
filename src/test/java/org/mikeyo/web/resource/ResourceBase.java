package org.mikeyo.web.resource;

import com.jayway.restassured.RestAssured;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mikeyo.web.WebConfig;
import org.mikeyo.web.WebDriver;

public class ResourceBase {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        driver = new WebDriver();
        driver.start();

        final Config config = ConfigFactory.load();
        RestAssured.baseURI="http://localhost";
        RestAssured.basePath="/";
        RestAssured.port=config.getInt(WebConfig.WEB_PORT.getKey());
    }

    @AfterClass
    public static void tearDown() {
        driver.stop();
    }
}
