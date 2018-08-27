package com.example.demo.heroes;

public class MissingExpectedPropertyException extends Exception {

    public MissingExpectedPropertyException(String name) {
        super("The expected property from file: '" + name + "' is missing.");
    }
}
