package com.waltersson.deepgram.sdk

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.matching.MatchResult
import com.github.tomakehurst.wiremock.matching.StringValuePattern
import com.waltersson.deepgram.model.*
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.test.StepVerifier
import java.math.BigDecimal

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsageTest {

    private val server = WireMockServer()

    @BeforeAll
    fun startServer() {
        server.start()
    }

    @Test
    fun getAllRequests() {
        stubFor(
            get(urlPathEqualTo("/projects/1/requests"))
                .withQueryParam("start", equalTo("2021-05-10T22:10:00Z"))
                .withQueryParam("end", equalTo("2021-05-10T22:10:00Z"))
                .withQueryParam("page", equalTo("1"))
                .withQueryParam("limit", equalTo("2"))
                .withQueryParam("status", equalTo("succeeded"))
                .willReturn(
                    okJson(
                        """
                        {
                            "limit": 0,
                            "page": 123,
                            "requests": [
                                $sampleRequestJson
                            ]
                        }
                    """
                    )
                )
        )
        val underTest = Deepgram("", server.baseUrl())
        val actual = underTest.usage().getAllRequests(
            "1",
            "2021-05-10T22:10:00Z",
            "2021-05-10T22:10:00Z",
            1,
            2,
            "succeeded"
        )
        StepVerifier.create(actual)
            .expectNext(
                RequestsPagedResponse()
                    .limit(0)
                    .page(123)
                    .requests(listOf(sampleRequest))
            )
            .verifyComplete()
    }

    @Test
    fun getRequest() {
        stubFor(
            get(urlPathEqualTo("/projects/1/requests/123"))
                .willReturn(okJson(sampleRequestJson))
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.usage().getRequest("1", "123")
        StepVerifier.create(actual)
            .expectNext(sampleRequest)
            .verifyComplete()
    }

    @Test
    fun summarizeUsage() {
        stubFor(
            get(urlPathEqualTo("/projects/1/usage"))
                .withQueryParam("start", equalTo("start"))
                .withQueryParam("end", equalTo("end"))
                .withQueryParam("accessor", equalTo("accessor2"))
                .withQueryParam("tag", equalTo("tag"))
                .withQueryParam("method", equalTo("method"))
                .withQueryParam("model", equalTo("model"))
                .withQueryParam("multichannel", equalTo("false"))
                .withQueryParam("interim_results", equalTo("false"))
                .withQueryParam("punctuate", equalTo("false"))
                .withQueryParam("replace", equalTo("false"))
                .withQueryParam("profanity_filter", equalTo("false"))
                .withQueryParam("keywords", equalTo("false"))
                .withQueryParam("sentiment", equalTo("false"))
                .withQueryParam("diarize", equalTo("false"))
                .withQueryParam("detect_language", equalTo("false"))
                .withQueryParam("search", equalTo("false"))
                .withQueryParam("redact", equalTo("false"))
                .withQueryParam("alternatives", equalTo("false"))
                .withQueryParam("numerals", equalTo("false"))
                .willReturn(okJson(sampleUsageJson))
        )

        val underTest = Deepgram("mykey", server.baseUrl())
        val actual = underTest.usage().summarize(
            "1",
            "start",
            "end",
            listOf("accessor2"),
            listOf("tag"),
            listOf("method"),
            listOf("model"),
            multichannel = false,
            interimResults = false,
            punctuate = false,
            replace = false,
            profanityFilter = false,
            keywords = false,
            sentiment = false,
            diarize = false,
            detectLanguage = false,
            search = false,
            redact = false,
            alternatives = false,
            numerals = false
        )
        StepVerifier.create(actual)
            .expectNext(sampleUsage)
            .verifyComplete()
    }

    // Test multiple accessor, tag, method, model values
    @AfterAll
    fun stopServer() {
        server.stop()
    }

    private val sampleUsageJson = """
        {
            "start": "2019-08-24T14:15:22Z",
            "end": "2019-08-24T14:15:22Z",
            "resolution": {
                "units": "string",
                "amount": 0
            },
            "results": [
                {
                    "start": "2019-08-24T14:15:22Z",
                    "end": "2019-08-24T14:15:22Z",
                    "hours": 0,
                    "requests": 0
                }
            ]
        }
    """

    private val sampleUsage = UsageSummary()
        .start("2019-08-24T14:15:22Z")
        .end("2019-08-24T14:15:22Z")
        .resolution(UsageSummaryResolution().units("string").amount(0))
        .addResultsItem(
            UsageResult()
                .start("2019-08-24T14:15:22Z")
                .end("2019-08-24T14:15:22Z")
                .hours(BigDecimal(0))
                .requests(0)
        )

    private val sampleRequestJson = """
        {
            "request_id": "rid",
            "created": "2019-08-24T14:15:22Z",
            "path": "path",
            "api_key_id": "key-id",
            "response": {
                "details": {
                    "usd": 1,
                    "duration": 2,
                    "total_audio": 3,
                    "channels": 4,
                    "streams": 5,
                    "model": "model",
                    "methods": "methods",
                    "tags": [
                        "tag"
                    ],
                    "features": [
                        "feature"
                    ],
                    "config": {
                        "diarize": true,
                        "multichannel": true
                    }
                },
                "code": 200,
                "completed": "2019-08-24T14:15:22Z"
            },
            "callback": {
                "code": 200,
                "completed": "2019-08-24T14:15:22Z"
            }
        }
    """

    private val sampleRequest = Request()
        .requestId("rid")
        .created("2019-08-24T14:15:22Z")
        .path("path")
        .apiKeyId("key-id")
        .response(
            Response()
                .details(
                    ResponseDetails()
                        .usd(BigDecimal(1))
                        .duration(BigDecimal(2))
                        .totalAudio(BigDecimal(3))
                        .channels(4)
                        .streams(5)
                        .model("model")
                        .methods("methods")
                        .tags(listOf("tag"))
                        .features(listOf("feature"))
                        .config(
                            ResponseDetailsConfig()
                                .diarize(true)
                                .multichannel(true)
                        )
                )
                .code(200)
                .completed("2019-08-24T14:15:22Z")

        )
        .callback(
            Callback()
                .code(200)
                .completed("2019-08-24T14:15:22Z")
        )
}