package yapp.be.client

import com.fasterxml.jackson.databind.ObjectMapper
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
                    text = text,
                    channel = channel,
                    username = botName,
                    icon_emoji = botIcon
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

data class SlackLogDeliveryRequest(
    val text: String,
    val channel: String,
    val username: String,
    val icon_emoji: String
)

sealed interface SlackLogDeliveryResponse {
    data class Success(
        val ok: Boolean,
        val channel: String,
        val ts: String,
        val message: Map<String, String>

    ) : SlackLogDeliveryResponse
    data class Fail(
        val ok: Boolean,
        val error: String
    ) : SlackLogDeliveryResponse
}
