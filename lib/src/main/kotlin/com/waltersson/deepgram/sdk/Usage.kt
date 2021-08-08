package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.Request
import com.waltersson.deepgram.model.RequestFields
import com.waltersson.deepgram.model.RequestsPagedResponse
import com.waltersson.deepgram.model.UsageSummary
import reactor.core.publisher.Mono

class Usage(private val api: DefaultApi) {

    @JvmOverloads
    fun listRequests(
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

    @JvmOverloads
    fun getFields(projectId: String, start: String? = null, end: String? = null): Mono<RequestFields> {
        return api.getFields(projectId, start, end)
    }

    fun getRequest(projectId: String, requestId: String): Mono<Request> {
        return api.getRequest(projectId, requestId)
    }

    @JvmOverloads
    fun summarize(
        projectId: String,
        start: String? = null,
        end: String? = null,
        accessor: List<String>? = null,
        tag: List<String>? = null,
        method: List<String>? = null,
        model: List<String>? = null,
        multichannel: Boolean? = null,
        interimResults: Boolean? = null,
        punctuate: Boolean? = null,
        replace: Boolean? = null,
        profanityFilter: Boolean? = null,
        keywords: Boolean? = null,
        sentiment: Boolean? = null,
        diarize: Boolean? = null,
        detectLanguage: Boolean? = null,
        search: Boolean? = null,
        redact: Boolean? = null,
        alternatives: Boolean? = null,
        numerals: Boolean? = null
    ): Mono<UsageSummary> {
        return api.getUsageSummary(
            projectId,
            start,
            end,
            accessor,
            tag,
            method,
            model,
            multichannel,
            interimResults,
            punctuate,
            replace,
            profanityFilter,
            keywords,
            sentiment,
            diarize,
            detectLanguage,
            search,
            redact,
            alternatives,
            numerals
        )
    }
}