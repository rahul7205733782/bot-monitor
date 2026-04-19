package com.example.demo;

public class Client {

    private String name;
    private String apiUrl;

    public Client(String name, String apiUrl) {
        this.name = name;
        this.apiUrl = apiUrl;
    }

    public String getName() {
        return name;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}