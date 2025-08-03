package com.CP.KPCOS.services.trello;

import com.CP.KPCOS.exceptions.ApiException;
import com.CP.KPCOS.payload.request.TrelloApiRequest;
import com.CP.KPCOS.payload.response.ITrelloApiBodyProcessor;
import com.CP.KPCOS.services.ApiService;
import com.CP.KPCOS.utils.CommonUtils;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class TrelloApiService {

    private static final String TRELLO_API_URL = "https://api.trello.com/1";
    private final RateLimiter rateLimiter = RateLimiter.create(15.0);

    @Autowired
    private ApiService apiService;

    private <T> T executeWithRetry(TrelloApiRequest request, Function<TrelloApiRequest, ResponseEntity<T>> executor) {
        int retryCount = 0;
        buildRequest(request);
        while (true){
            try {
                rateLimiter.acquire();
                return executor.apply(request).getBody();
            } catch (ApiException e) {
                retryCount++;
                log.error("Error executing request: {}, retry count: {}", request.getCallUrl(), retryCount, e);
                handleRetryOrThrow(e, retryCount, request);
            }
        }
    }

    public <T> T get(TrelloApiRequest request) {
        return (T) executeWithRetry(request, req -> apiService.get(req.getCallUrl(), req.getHeaders(), req.getParameterizedTypeReference()));
    }

    public void buildRequest(TrelloApiRequest request) {
        if (request.getTrelloToken() != null && !CommonUtils.isEmptyString(request.getTrelloToken().getTrelloApiToken())) {
            request.addParameter("token", request.getTrelloToken().getTrelloApiToken());
        }
        if (!request.getCallUrl().startsWith("/")) {
            request.setCallUrl("/" + request.getCallUrl());
        }
        StringBuilder sb = new StringBuilder(request.getCallUrl());
        if (request.getQueryParams() != null && !request.getQueryParams().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getQueryParams().entrySet()) {
                sb.append(sb.indexOf("?") == -1 ? "?" : "&");
                sb.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        String url = TRELLO_API_URL + sb.toString();
        if (request.getPathVariables() != null && !request.getPathVariables().isEmpty()) {
            for (Map.Entry<String, String> entry : request.getPathVariables().entrySet()) {
                url = url.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        request.setCallUrl(url);
        processRequestBody(request);
    }

    public void processRequestBody(TrelloApiRequest request) {
        Object requestBody = request.getBody();
        if (requestBody == null){
            return;
        }
        if (requestBody instanceof ITrelloApiBodyProcessor) {
            request.setBody(((ITrelloApiBodyProcessor) requestBody).processBody());
        }
        else {
            throw new ApiException("Request body must implement ITrelloApiBodyProcessor", 500);
        }
    }

    public void handleRetryOrThrow(ApiException e, int retryCount, TrelloApiRequest request) {
        if (retryCount >= request.getRetry()){
            log.error("Max retries reached for request: {}", request.getCallUrl(), e);
            throw new ApiException("Max retries reached for request: " + request.getCallUrl(), e);
        }
        int statusCode = e.getStatusCode();
        if (statusCode == 429) {
            log.warn("Rate limit exceeded for request: {}, retrying...", request.getCallUrl());
            try {
                Thread.sleep(1000); // Wait before retrying
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new ApiException("Thread interrupted during retry wait", ie);
            }
        }
        log.info("Retrying request: {}", request.getCallUrl(), e);
    }
}
