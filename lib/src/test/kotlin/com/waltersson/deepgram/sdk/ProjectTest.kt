package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.waltersson.deepgram.api.DefaultApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance

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

        val underTest = DefaultApi()
        underTest.apiClient.basePath = server.baseUrl()
        val actual = underTest.getProject("1")
        assertThat(actual.name).isEqualTo("name")
        assertThat(actual.company).isEqualTo("acmeinc")
        assertThat(actual.projectId).isEqualTo("1")
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
        assertThat(actual).hasSize(1)
        assertThat(actual.first().name).isEqualTo("first project")
    }

    @AfterAll
    fun stopServer() {
        server.stop()
    }
}
