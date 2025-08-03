package com.CP.KPCOS.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        RestTemplate restTemplate = builder.build();
        MappingJackson2HttpMessageConverter jsonConverter = restTemplate.getMessageConverters().stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .map(converter -> (MappingJackson2HttpMessageConverter) converter)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No MappingJackson2HttpMessageConverter found"));

        // Add custom deserializer for Timestamp
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Timestamp.class, new TimestampDeserializer());
        objectMapper.registerModule(module);

        // config ObjectMapper
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        jsonConverter.setObjectMapper(objectMapper);
        return restTemplate;
    }

    public static class TimestampDeserializer extends JsonDeserializer<Timestamp> {
        @Override
        public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws java.io.IOException {
            long time = p.getLongValue();
            return  new Timestamp(time >  999999999999L ? time : time * 1000);
        }
    }
}
