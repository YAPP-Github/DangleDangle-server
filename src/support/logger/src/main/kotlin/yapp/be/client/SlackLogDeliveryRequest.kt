package yapp.be.client

data class SlackLogDeliveryRequest(
    val channel: String,
    val username: String,
    val icon_emoji: String,
    val attachments: List<SlackAttachment>
)

data class SlackAttachment(
    val color: String,
    val title: String,
    val text: String,
)
