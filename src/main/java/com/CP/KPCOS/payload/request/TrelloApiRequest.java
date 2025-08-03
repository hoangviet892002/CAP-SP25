package com.CP.KPCOS.payload.request;


import com.CP.KPCOS.dtos.response.BaseList;
import com.CP.KPCOS.entity.trello.TrelloTokenEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class TrelloApiRequest {
    @Value("${spring.application.trello.api-key}")
    private static String apiKey = "f3e1b910789ea4d87b5eb58c76a686ab";

    public enum ResponseType {
        Single, Search
    }

    @Builder.Default
    private HttpHeaders headers = new HttpHeaders(defaultHeaders());
    private String callUrl;
    private Object body;
    private String trelloApiKey;
    private TrelloTokenEntity trelloToken;

    @Builder.Default
    private Map<String, String> queryParams = new HashMap<>(defaultQueryParams());
    @Builder.Default
    private Class<?> baseType = Object.class;
    @Builder.Default
    private ResponseType responseType = ResponseType.Single;
    @Builder.Default
    private Map<String, String> pathVariables = new HashMap<>();
    @Builder.Default
    private int retry = 1;

    public void addParameter(String key, String value) {
        if (queryParams == null) {
            queryParams = new HashMap<>();
        }
        queryParams.put(key, value);
    }

    public void addPathVariable(String key, String value) {
        if (pathVariables == null) {
            pathVariables = new HashMap<>();
        }
        pathVariables.put(key, value);
    }

    public void addHeader(String key, String value) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.add(key, value);
    }

    public ParameterizedTypeReference<?> getParameterizedTypeReference() {
        if (Objects.requireNonNull(responseType) == ResponseType.Search) {
            // Tạo ParameterizedType cho List<T>
            Type listType = new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new Type[]{baseType};
                }

                @Override
                public Type getRawType() {
                    return List.class;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }
            };

            return ParameterizedTypeReference.forType(listType);
        }
        return ParameterizedTypeReference.forType(baseType);
    }
    private static Map<String, String> defaultQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("key", apiKey);
        return params;
    }

    // default headers
    private static HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        return headers;
    }
}
