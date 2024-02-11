package com.example.streaminggeminiapp;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}
