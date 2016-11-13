package org.mikeyo.web;

public enum WebConfig {

    /**
     * The port the web app should run on
     */
    WEB_PORT

    ;

    public String getKey() {
        return this.name().toLowerCase().replace("_", ".");
    }
}
