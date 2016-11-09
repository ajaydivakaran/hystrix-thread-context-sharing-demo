package com.example;

import com.example.reporting.RequestCorrelation;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        final String correlationId = request.getHeader("x-correlation-id");
        if (correlationId != null) {
            RequestCorrelation.setId(correlationId);
        } else {
            RequestCorrelation.setId(UUID.randomUUID().toString());
        }
        return true;
    }
}
