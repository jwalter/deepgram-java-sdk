package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Project
import com.waltersson.deepgram.model.Projects
import reactor.core.publisher.Mono

/*
 * Add Builder to make construction easier from Java?
 */
class Deepgram(private val apiKey: String, baseUrl: String = "https://api.deepgram.com/v1") {

    private val client = DefaultApi().apply {
        apiClient.basePath = baseUrl
        apiClient.setApiKeyPrefix("Token")
        apiClient.setApiKey(apiKey)
    }

    fun getProject(id: String): Mono<Project> {
        return client.getProject(id)
    }

    fun getProjects(): Mono<Projects> {
        return client.getProjects()
    }

}