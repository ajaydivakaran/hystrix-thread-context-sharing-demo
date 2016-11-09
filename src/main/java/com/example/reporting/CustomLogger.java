package com.example.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void log(String message) {
        logger.info("Thread name: " + Thread.currentThread().getName() + ". Message:" + message);
    }
}
