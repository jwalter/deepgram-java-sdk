package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.waltersson.deepgram.model.Project
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectsTest {

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
                    okJson(
                        """
                        {
                        "company": "acmeinc",
                        "name": "name",
                        "project_id": "1"
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.projects().get("1")
        StepVerifier.create(actual)
            .expectNextMatches { it.name == "name" }
            .verifyComplete()
    }

    @Test
    fun patchProject() {
        stubFor(
            patch(urlEqualTo("/projects/1"))
                .withRequestBody(
                    equalToJson(
                        """
                    {
                        "name": "new name",
                        "company": "new company"
                    }
                """.trimIndent()
                    )
                )
                .willReturn(
                    okJson(
                        """
                        {
                        "message": "This is a test"
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.projects().update(
            Project().projectId("1").name("new name").company("new company")
        )
        StepVerifier.create(actual)
            .expectNextMatches { it.message == "This is a test" }
            .verifyComplete()
    }

    @Test
    fun getAllProjects() {
        stubFor(
            get("/projects")
                .willReturn(
                    okJson(
                        """
                        {
                            "projects": [
                                {
                                "company": "acmeinc",
                                "name": "first project",
                                "project_id": "1"
                                }
                            ]
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("", server.baseUrl())
        val actual = underTest.projects().list()
        StepVerifier.create(actual)
            .expectNextMatches { it.projects?.size == 1 }
            .verifyComplete()
    }

    @AfterAll
    fun stopServer() {
        server.stop()
    }
}
