//WebConfig.java
package com.sick.apeuda.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Timestamp.class, new com.fasterxml.jackson.databind.JsonDeserializer<>() {
            @Override
            public Timestamp deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws java.io.IOException {
                String str = p.getText().trim();
                try {
                    return Timestamp.valueOf(LocalDateTime.parse(str, DateTimeFormatter.ISO_DATE_TIME));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        module.addSerializer(Timestamp.class, new com.fasterxml.jackson.databind.JsonSerializer<>() {
            @Override
            public void serialize(Timestamp value, com.fasterxml.jackson.core.JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws java.io.IOException {
                gen.writeString(value.toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
            }
        });
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
