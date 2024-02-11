package com.example.streaminggeminiapp;

import android.util.Log;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Collections;
import java.util.concurrent.Executor;

import kotlin.contracts.ContractBuilder;

public class GeminiPro {
    public void getResponse(String query, ResponseCallback callback) {
        GenerativeModelFutures model = getModel();

        Content.Builder contentBuilder = new Content.Builder();
        contentBuilder.setRole("user");
        contentBuilder.addText(query);
        Content content = contentBuilder.build();

        Log.d("GeminiPro", "getResponse: " + query);
        Log.d("GeminiPro", "getResponse: " + content);

        final String[] fullResponse = {""};

        Publisher<GenerateContentResponse> streamingResponse = GenerativeModelFuturesExtension.Companion.generateContentStream(model, content);

        streamingResponse.subscribe(new Subscriber<GenerateContentResponse>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(GenerateContentResponse generateContentResponse) {
                String chunk = generateContentResponse.getText();
                fullResponse[0] += chunk;
                callback.onResponse(fullResponse[0]);
                Log.d("GeminiPro", "onNext: " + chunk);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                callback.onError(t);
                Log.d("GeminiPro", "onError: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println(fullResponse[0]);
                callback.onResponse(fullResponse[0]);
                Log.d("GeminiPro", "onComplete: " + fullResponse[0]);
            }
        });
    }

    private GenerativeModelFutures getModel() {
        String apiKey = BuildConfig.apiKey;

        SafetySetting harassmentSafety = new SafetySetting(HarmCategory.HARASSMENT,
                BlockThreshold.ONLY_HIGH);

        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;
        GenerationConfig generationConfig = configBuilder.build();

        GenerativeModel gm = new GenerativeModel(
                "gemini-pro",
                apiKey,
                generationConfig,
                Collections.singletonList(harassmentSafety)
        );

        return GenerativeModelFutures.from(gm);
    }
}
