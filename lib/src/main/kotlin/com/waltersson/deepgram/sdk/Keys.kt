package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Key
import com.waltersson.deepgram.model.KeyCreate
import com.waltersson.deepgram.model.KeyCreated
import com.waltersson.deepgram.model.Keys
import reactor.core.publisher.Mono

class Keys(private val api: DefaultApi) {

    fun get(projectId: String, keyId: String): Mono<Key> {
        return api.getKey(projectId, keyId)
    }

    fun list(projectId: String): Mono<Keys> {
        return api.getKeys(projectId)
    }

    fun create(projectId: String, comment: String, scopes: List<String>): Mono<KeyCreated> {
        return api.createKey(projectId, KeyCreate().comment(comment).scopes(scopes))
    }

    fun delete(projectId: String, keyId: String): Mono<Void> {
        return api.deleteKey(projectId, keyId)
    }

}