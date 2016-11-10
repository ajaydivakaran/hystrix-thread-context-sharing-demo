package com.example.client;

import com.example.logging.CustomLogger;
import com.example.logging.RequestCorrelation;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Collections;

public class CorrelationIdAwareRestTemplateClient extends AsyncRestTemplate {


    public CorrelationIdAwareRestTemplateClient(AsyncListenableTaskExecutor taskExecutor) {
        super(taskExecutor);
        this.setInterceptors(Collections.singletonList((request, body, execution) -> {
            final String id = RequestCorrelation.getId();
            new CustomLogger().log("RestClient correlation id: " + id);
            request.getHeaders().put("x-correlation-id", Collections.singletonList(id));
            return execution.executeAsync(request, body);
        }));
    }
}
