package org.mikeyo.web;

public class Response {

    public static ResponseCreator ok(final String body) {
        return (req, res) -> {
            res.status(200); // ok
            res.type("application/json");

            return body;
        };
    }

    public static ResponseCreator created(final String body) {
        return (req, res) -> {
            res.status(201); // created
            res.type("application/json");

            return body;
        };
    }

    public static ResponseCreator noContent() {
        return (req, res) -> {
            res.status(204); // no content
            return "";
        };
    }

    public static ResponseCreator badRequest(final String body) {
        return (req, res) -> {
            res.status(400); // bad request
            res.type("application/json");

            return body;
        };
    }
}
