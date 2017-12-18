package ru.SilirdCo.Luxoft.Tests.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class DefaultValue {
    private final static Logger logger = LoggerFactory.getLogger(DefaultValue.class);

    private String name;

    private int counter = 0;

    public DefaultValue(String name) {
        this.name = name;
    }

    public Object getValue(String type) {
        switch (type) {
            case "String": {
                return name + counter;
            }
            case "Float": {
                return Float.parseFloat(String.valueOf(counter));
            }
            case "Long": {
                return Long.parseLong(String.valueOf(counter));
            }
            case "Integer": {
                return counter;
            }
            case "Boolean": {
                return true;
            }
            case "Date": {
                return new Date();
            }
            default: {
                logger.warn("Неизвестный тип данных: " + type);
                return null;
            }
        }
    }

    public Object getUpdatedValue(String type) {
        switch (type) {
            case "String": {
                return "updated" + counter;
            }
            case "Float": {
                return Float.parseFloat(String.valueOf(-counter));
            }
            case "Long": {
                return Long.parseLong(String.valueOf(-counter));
            }
            case "Integer": {
                return -counter;
            }
            case "Boolean": {
                return false;
            }
            default: {
                return getValue(type);
            }
        }
    }

    public void incrementCounter() {
        counter++;
    }
}
