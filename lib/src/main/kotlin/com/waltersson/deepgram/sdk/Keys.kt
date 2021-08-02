package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Key
import com.waltersson.deepgram.model.Keys
import reactor.core.publisher.Mono

class Keys(private val api: DefaultApi) {

    fun get(projectId: String, keyId: String): Mono<Key> {
        return api.getKey(projectId, keyId)
    }

    fun list(projectId: String): Mono<Keys> {
        return api.getKeys(projectId)
    }

}