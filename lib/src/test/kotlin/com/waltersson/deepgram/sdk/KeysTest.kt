package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class KeysTest {

    private val server = WireMockServer()

    @BeforeEach
    fun startServer() {
        server.start()
    }

    @Test
    fun getKey() {
        stubFor(
            get("/projects/1/keys/1")
                .withHeader("Authorization", equalTo("Token mykey"))
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

    // NYI Get key for non-existing project

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

    @Test
    fun createKey() {
        stubFor(
            post("/projects/1/keys")
                .withHeader("Authorization", equalTo("Token mykey"))
                .withRequestBody(
                    equalToJson(
                        """
                            {
                                "comment": "comment",
                                "scopes": ["scope"]
                            }
                        """
                    )
                )
                .willReturn(
                    okJson(
                        """
                        {
                            "key": "key-value",
                            "api_key_id": "key-id",
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
        val actual = underTest.keys().create("1", "comment", listOf("scope"))
        StepVerifier.create(actual)
            .expectNextMatches { it.key == "key-value" }
            .verifyComplete()
    }

    @Test
    fun deleteKey() {
        stubFor(
            delete("/projects/1/keys/123")
                .withHeader("Authorization", equalTo("Token mykey"))
                .willReturn(
                    ok()
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.keys().delete("1", "123")
        StepVerifier.create(actual)
            .expectNext()
            .verifyComplete()
    }

    @AfterEach
    fun stopServer() {
        server.stop()
    }
}
