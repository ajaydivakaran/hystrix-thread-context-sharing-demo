package com.example.client;

import com.example.reporting.CustomLogger;
import com.example.reporting.RequestCorrelation;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;

@Component
public class CorrelationIdAwareRestTemplateClient extends RestTemplate {


    public CorrelationIdAwareRestTemplateClient() {
        super();
        this.setInterceptors(Collections.singletonList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution
                    execution) throws IOException {
                final String id = RequestCorrelation.getId();
                new CustomLogger().log("RestClient correlation id: " + id);
                request.getHeaders().put("x-correlation-id", Collections.singletonList(id));
                return execution.execute(request, body);
            }
        }));
    }
}
