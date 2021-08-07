package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi

/*
 * Add Builder to make construction easier from Java?
 */
class Deepgram(private val apiKey: String, baseUrl: String = "https://api.deepgram.com/v1") {

    private val client = DefaultApi().apply {
        apiClient.basePath = baseUrl
        apiClient.setApiKeyPrefix("Token")
        apiClient.setApiKey(apiKey)
    }
    private val projectApi = Projects(client)
    private val keysApi = Keys(client)
    private val membersApi = Members(client)
    private val scopesApi = Scopes(client)
    private val usageApi = Usage(client)

    fun projects() = projectApi
    fun keys() = keysApi
    fun members() = membersApi
    fun scopes() = scopesApi
    fun usage() = usageApi
}

