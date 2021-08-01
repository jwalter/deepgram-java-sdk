package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.waltersson.deepgram.api.DefaultApi
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectTest {

    private val server = WireMockServer()

    @BeforeAll
    fun startServer() {
        server.start()
    }

    @Test
    fun getSingleProject() {
        stubFor(
            get("/projects/1")
                .withHeader("Authorization", equalTo("Token mykey"))
                .willReturn(
                    okJson("""
                        {
                        "company": "acmeinc",
                        "name": "name",
                        "project_id": "1"
                        }
                    """)
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())

        // underTest.apiClient.basePath = server.baseUrl()
        val actual = underTest.getProject("1")
        StepVerifier.create(actual)
            .expectNextMatches { it.name == "name"}
            .verifyComplete()
    }

    @Test
    fun getAllProjects() {
        stubFor(
            get("/projects")
                .willReturn(
                    okJson("""
                        [{
                        "company": "acmeinc",
                        "name": "first project",
                        "project_id": "1"
                        }]
                    """)
                )
        )

        val underTest = DefaultApi()
        underTest.apiClient.basePath = server.baseUrl()
        val actual = underTest.getProjects()
        StepVerifier.create(actual)
            .expectNextCount(1)
            .verifyComplete()
    }

    @AfterAll
    fun stopServer() {
        server.stop()
    }
}
