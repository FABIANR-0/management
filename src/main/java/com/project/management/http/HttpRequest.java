package com.project.management.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class HttpRequest {

    private static final String MESSAGE = "Estamos teniendo problemas, reinténtalo más tarde.";

    public static HttpResponse<String> httpRequestPost(String url, Object jsonRequest) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
            java.net.http.HttpRequest httpRequest = java.net.http.HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(java.net.http.HttpRequest.BodyPublishers.ofString(gson.toJson(jsonRequest)))
                    .timeout(Duration.ofSeconds(20))
                    .build();
            HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            log.error("Error en la petición POST {}", ex.getMessage());
            throw new IllegalArgumentException(MESSAGE);
        }
    }

    public static HttpResponse<String> httpRequestGet(String url, String accessToken) {
        try {
            java.net.http.HttpRequest.Builder requestBuilder  = java.net.http.HttpRequest
                    .newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10));
            if (accessToken != null) {
                requestBuilder .header("Authorization", "Bearer " + accessToken);
            }
            HttpClient httpClient = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            return httpClient.send(requestBuilder.GET().build(), HttpResponse.BodyHandlers.ofString());
        } catch (Exception ex) {
            log.error("Error en la petición GET {}", ex.getMessage());
            throw new IllegalArgumentException(MESSAGE);
        }
    }
}
