package com.waltersson.deepgram.sdk

import com.waltersson.deepgram.api.DefaultApi
import com.waltersson.deepgram.model.MessageResponse
import com.waltersson.deepgram.model.Project
import com.waltersson.deepgram.model.ProjectUpdate
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
    private val projectApi = Projects(client)

    fun projects() = projectApi

}

class Projects(private val api: DefaultApi) {
    fun list(): Mono<Projects> {
        return api.projects
    }

    fun get(id: String): Mono<Project> {
        return api.getProject(id)
    }

    fun update(project: Project): Mono<MessageResponse> {
        val update = ProjectUpdate().name(project.name).company(project.company)
        return api.updateProject(project.projectId, update)
    }
}