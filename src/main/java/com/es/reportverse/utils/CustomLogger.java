package com.es.reportverse.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CustomLogger {
    private final Logger log4j;

    @Setter
    private String context;

    public CustomLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Class<?> loggerClass = null;
        if (stackTrace.length >= 3) {
            StackTraceElement caller = stackTrace[2];
            loggerClass = caller.getClass();
        }

        this.log4j = LogManager.getLogger(loggerClass);
    }

    private String mapToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logMethodStart(Object object) {
        log4j.info(this.context + " started receiving object {}", mapToJsonString(object));
    }

    public void logMethodStart() {
        log4j.info(this.context + " started");
    }

    public void logMethodEnd(Object object) {
        log4j.info(this.context + " finished returning object {}", mapToJsonString(object));
    }

    public void logMethodEnd() {
        log4j.info(this.context + " finished");
    }

}
