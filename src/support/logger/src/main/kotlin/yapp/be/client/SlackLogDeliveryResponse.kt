package yapp.be.client

sealed interface SlackLogDeliveryResponse {
    data class Success(
        val ok: Boolean,
        val channel: String,
        val ts: String,
        val message: SlackLogDeliveryMessageDto

    ) : SlackLogDeliveryResponse {
        data class SlackLogDeliveryMessageDto(
            val type: String,
            val subtype: String,
            val text: String
        )
    }
    data class Fail(
        val ok: Boolean,
        val error: String
    ) : SlackLogDeliveryResponse
}
