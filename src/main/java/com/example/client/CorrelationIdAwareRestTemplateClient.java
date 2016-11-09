package com.example.client;

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
                request.getHeaders().put("x-correlation-id", Collections.singletonList(RequestCorrelation.getId()));
                return execution.execute(request, body);
            }
        }));
    }
}
