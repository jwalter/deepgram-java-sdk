package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.waltersson.deepgram.model.MessageResponse
import com.waltersson.deepgram.model.Project
import com.waltersson.deepgram.model.Scope
import com.waltersson.deepgram.model.Scopes
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScopesTest {

    private val server = WireMockServer()

    @BeforeAll
    fun startServer() {
        server.start()
    }

    @Test
    fun getMemberScopes() {
        stubFor(
            get("/projects/1/members/123/scopes")
                .willReturn(
                    okJson(
                        """
                        {
                            "scopes": ["scope"]
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("", server.baseUrl())
        val actual = underTest.scopes().list("1", "123")
        StepVerifier.create(actual)
            .expectNext(Scopes().scopes(listOf("scope")))
            .verifyComplete()
    }

    @Test
    fun updateScope() {
        stubFor(
            put(urlEqualTo("/projects/1/members/123/scopes"))
                .withRequestBody(
                    equalToJson(
                        """
                            {
                                "scope": "new scope"
                            }
                        """
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
        val actual = underTest.scopes().update(
            "1", "123", Scope().scope("new scope")
        )
        StepVerifier.create(actual)
            .expectNext(MessageResponse().message("This is a test"))
            .verifyComplete()
    }

    @AfterAll
    fun stopServer() {
        server.stop()
    }
}
