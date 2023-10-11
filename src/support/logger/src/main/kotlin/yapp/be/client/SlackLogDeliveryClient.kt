package yapp.be.client

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
    fun send(
        token: String,
        channel: String,
        botName: String,
        botIcon: String,
        text: String
    ): Mono<SlackLogDeliveryResponse> {
        return instance
            .post()
            .header("Authorization", "Bearer $token")
            .bodyValue(
                SlackLogDeliveryRequest(
                    channel = channel,
                    username = botName,
                    icon_emoji = botIcon,
                    attachments = buildList {
                        add(
                            SlackAttachment(
                                color = "#FF0000",
                                title = ":red_circle:  ===[Error]===",
                                text = text
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
        println(responseBody)
        return if (responseBody.contains("\"ok\":true")) {
            objectMapper.readValue(responseBody, SlackLogDeliveryResponse.Success::class.java)
        } else {
            objectMapper.readValue(responseBody, SlackLogDeliveryResponse.Fail::class.java)
        }
    }
}
