package com.example.vieshow;

/** Shared PHP endpoint configuration for the original Activity classes. */
public final class ApiConfig {
    private ApiConfig() {
    }

    public static String url(String endpoint) {
        String baseUrl = BuildConfig.API_BASE_URL;
        return (baseUrl.endsWith("/") ? baseUrl : baseUrl + "/") + endpoint;
    }
}
