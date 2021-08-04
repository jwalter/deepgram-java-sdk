package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Key
import com.waltersson.deepgram.model.KeyCreate
import com.waltersson.deepgram.model.KeyCreated
import com.waltersson.deepgram.model.Keys
import com.waltersson.deepgram.model.Members
import reactor.core.publisher.Mono

class Members(private val api: DefaultApi) {

    fun list(projectId: String): Mono<Members> {
        return api.getMembers(projectId)
    }

    fun remove(projectId: String, memberId: String): Mono<Void> {
        return api.removeMember(projectId, memberId)
    }

}