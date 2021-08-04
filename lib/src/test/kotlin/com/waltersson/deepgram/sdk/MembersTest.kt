package com.waltersson.deepgram.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.waltersson.deepgram.model.MemberComplete
import com.waltersson.deepgram.model.Members
import com.waltersson.deepgram.model.Project
import org.junit.jupiter.api.*
import reactor.test.StepVerifier

class MembersTest {

    private val server = WireMockServer()

    @BeforeEach
    fun startServer() {
        server.start()
    }

    @Test
    fun getMembers() {
        stubFor(
            get("/projects/1/members")
                .willReturn(
                    okJson(
                        """
                        {
                            "members": [
                                {
                                    "member_id": "id",
                                    "first_name": "first",
                                    "last_name": "last",
                                    "scopes": [ "scope" ],
                                    "email": "email"
                                }
                            ]
                        }
                    """
                    )
                )
        )

        val underTest = Deepgram("", server.baseUrl())
        val actual = underTest.members().list("1")
        StepVerifier.create(actual)
            .expectNext(Members().members(
                listOf(MemberComplete()
                    .memberId("id")
                    .firstName("first")
                    .lastName("last")
                    .scopes(listOf("scope"))
                    .email("email"))
            ))
            .verifyComplete()
    }

    @Test
    fun removeMember() {
        stubFor(
            delete("/projects/1/members/123")
                .withHeader("Authorization", equalTo("Token mykey"))
                .willReturn(
                    ok()
                )
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.members().remove("1", "123")
        StepVerifier.create(actual)
            .expectNext()
            .verifyComplete()
    }

    @AfterEach
    fun stopServer() {
        server.stop()
    }
}
