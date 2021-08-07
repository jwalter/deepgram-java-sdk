package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Request
import com.waltersson.deepgram.model.RequestsPagedResponse
import com.waltersson.deepgram.model.UsageSummary
import reactor.core.publisher.Mono

class Usage(private val api: DefaultApi) {

    fun getAllRequests(
        projectId: String,
        start: String? = null,
        end: String? = null,
        page: Int? = null,
        limit: Int? = null,
        status: String? = null
    ): Mono<RequestsPagedResponse> {
        return api.getAllRequests(
            projectId,
            start,
            end,
            page,
            limit,
            status
        )
    }

    fun getRequest(projectId: String, requestId: String): Mono<Request> {
        return api.getRequest(projectId, requestId)
    }

    fun summarize(projectId: String): Mono<UsageSummary> {
        return api.getUsageSummary(
            projectId,
            "string",
            "string",
            "string",
            "string",
            "string",
            "string",
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        )
    }
}