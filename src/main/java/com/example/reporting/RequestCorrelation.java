package com.example.reporting;

public class RequestCorrelation {

    private static final ThreadLocal<String> id = new ThreadLocal<>();

    public static String getId() {
        return id.get();
    }

    public static void setId(String correlationId) {
        id.set(correlationId);
    }
}

