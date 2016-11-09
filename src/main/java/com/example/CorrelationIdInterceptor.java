package com.example;

import com.example.reporting.CustomLogger;
import com.example.reporting.RequestCorrelation;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CorrelationIdInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (!currentRequestIsAsyncDispatcher(request)) {
            final String correlationId = UUID.randomUUID().toString();
            RequestCorrelation.setId(correlationId);
            new CustomLogger().log("Interceptor correlation id: " + correlationId);
        }

        return true;
    }

    private boolean currentRequestIsAsyncDispatcher(HttpServletRequest request) {
        return request.getDispatcherType().equals(DispatcherType.ASYNC);
    }
}

