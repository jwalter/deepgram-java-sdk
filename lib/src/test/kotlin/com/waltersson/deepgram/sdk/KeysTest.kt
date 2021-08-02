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
class KeysTest {

    private val server = WireMockServer()

    @BeforeAll
    fun startServer() {
        server.start()
    }

    @Test
    fun getSingleKey() {
        stubFor(
            get("/projects/1/keys/1")
                .willReturn(
                    okJson(
                        """
                        {
                            "api_key_id": "key",
                            "comment": "comment",
                            "created": "2019-08-24T14:15:22Z",
                            "scopes": [
                                "scope"
                            ]
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.keys().get("1", "1")
        StepVerifier.create(actual)
            .expectNextMatches { it.apiKeyId == "key" }
            .verifyComplete()
    }

    @Test
    fun getAllKeys() {
        stubFor(
            get("/projects/1/keys")
                .willReturn(
                    okJson(
                        """
                        {
                            "api_keys": [
                                {
                                    "member": {
                                        "member_id": "memberid",
                                        "email": "email"
                                    },
                                    "api_key": {
                                        "api_key_id": "keyid",
                                        "comment": "mycomment",
                                        "scopes": ["scoops"],
                                        "created": "2019-08-24T14:15:22Z"
                                    }
                                }
                            ]
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("", server.baseUrl())
        val actual = underTest.keys().list("1")
        StepVerifier.create(actual)
            .expectNextMatches { it.apiKeys?.size == 1 }
            .verifyComplete()
    }

    @AfterAll
    fun stopServer() {
        server.stop()
    }
}
