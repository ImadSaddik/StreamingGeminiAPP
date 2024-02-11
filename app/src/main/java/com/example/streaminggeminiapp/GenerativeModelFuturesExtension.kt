package com.example.streaminggeminiapp

import com.google.ai.client.generativeai.java.GenerativeModelFutures
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.reactive.asPublisher
import org.reactivestreams.Publisher

class GenerativeModelFuturesExtension {
    companion object {
        fun generateContentStream(
                model: GenerativeModelFutures,
                vararg prompt: Content
        ): Publisher<GenerateContentResponse> =
                model.getGenerativeModel().generateContentStream(*prompt).asPublisher()
    }
}