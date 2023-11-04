package yapp.be.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class SlackLogDeliveryClient {
    companion object {
        private val instance = WebClient
            .builder()
            .baseUrl("https://slack.com/api/chat.postMessage")
            .build()
        private val objectMapper = ObjectMapper()
            .registerModule(KotlinModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    fun sendAsMessage(
        title: String,
        token: String,
        channel: String,
        botName: String,
        botIcon: String,
        text: String,
        traceId: String,
        footer: String,
    ) = send(title, token, null, channel, botName, botIcon, text, traceId, footer)

    fun sendAsThread(
        token: String,
        channel: String,
        botName: String,
        botIcon: String,
        ts: String?,
        traceId: String,
        titleAndText: Map<String, String>,
    ) {
        titleAndText.forEach { (title, text) -> send(title, token, ts, channel, botName, botIcon, text, traceId, null).subscribe() }
    }

    private fun send(
        title: String,
        token: String,
        ts: String?,
        channel: String,
        botName: String,
        botIcon: String,
        text: String,
        traceId: String,
        footer: String?
    ): Mono<SlackLogDeliveryResponse> {
        return instance
            .post()
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", "Bearer $token")
            .bodyValue(
                SlackLogDeliveryRequest(
                    channel = channel,
                    thread_ts = ts,
                    username = botName,
                    icon_emoji = botIcon,
                    attachments = buildList {
                        add(
                            SlackAttachment(
                                color = "#FF0000",
                                title = title,
                                pretext = "[$traceId]",
                                text = text,
                                footer = footer
                            )
                        )
                    }
                )
            )
            .retrieve()
            .bodyToMono<String>()
            .map { parseSlackResponse(it) }
            .toMono()
    }
    private fun parseSlackResponse(responseBody: String): SlackLogDeliveryResponse {
        return if (responseBody.contains("\"ok\":true")) {
            objectMapper.readValue(responseBody, SlackLogDeliveryResponse.Success::class.java)
        } else {
            objectMapper.readValue(responseBody, SlackLogDeliveryResponse.Fail::class.java)
        }
    }
}
