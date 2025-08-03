package com.CP.KPCOS.services;

import com.CP.KPCOS.exceptions.ApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ApiService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public <T>ResponseEntity<T> get(String url, HttpHeaders headers, Class<T> responseType) {
        try {
            HttpEntity<String> request = new HttpEntity<>(headers);
            log.info("Sending GET request to URL: {}", url);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
            handleErrorOrThrow(response);
            return response;
        } catch (Exception e) {
            log.error("Error during GET request to {}: {}", url, e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    public <T> ResponseEntity<T> get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        try {
            HttpEntity<String> request = new HttpEntity<>(headers);
            log.info("Sending GET request to URL: {}", url);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
            handleErrorOrThrow(response);
            return response;
        } catch (Exception e) {
            log.error("Error during GET request to {}: {}", url, e.getMessage(), e);
            throw new ApiException(e);
        }
    }

    private void handleErrorOrThrow(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            String message = "Error message ";
            try {
                message = objectMapper.writeValueAsString(response.getBody());
            }
            catch (Exception e) {
                log.error("Error serializing response body: {}", e.getMessage(), e);
            }
            log.error("Error response from API: {}", message);
            throw new RuntimeException("API call failed with status: " + response.getStatusCode() + " and message: " + message);
        }
    }
}
