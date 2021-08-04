package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.MessageResponse
import com.waltersson.deepgram.model.Scope
import com.waltersson.deepgram.model.Scopes
import reactor.core.publisher.Mono

class Scopes(private val api: DefaultApi) {

    fun list(projectId: String, memberId: String): Mono<Scopes> {
        return api.getMemberScopes(projectId, memberId)
    }

    fun update(projectId: String, memberId: String, scope: Scope): Mono<MessageResponse> {
        return api.updateScope(projectId, memberId, scope)
    }

}