package com.sick.apeuda.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class IamportService {
    @Value("${iamport.api.key}")
    private String apiKey;

    @Value("${iamport.api.secret}")
    private String apiSecret;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public IamportService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }


    public String getToken() throws Exception {
        String requestBody = String.format("{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", apiKey, apiSecret);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.iamport.kr/users/getToken"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode responseJson = objectMapper.readTree(response.body());

        return responseJson.path("response").path("access_token").asText();
    }

    public JsonNode schedulePayment(String token, JsonNode scheduleData) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.iamport.kr/subscribe/payments/schedule"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(scheduleData.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
    public JsonNode unschedulePayment(String token, String customerUid) throws Exception {
        String requestBody = String.format("{\"customer_uid\":\"%s\"}", customerUid);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.iamport.kr/subscribe/payments/unschedule"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }
}
