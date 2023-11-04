package yapp.be.client

data class SlackLogDeliveryRequest(
    val channel: String,
    val username: String,
    val icon_emoji: String,
    val thread_ts: String?,
    val attachments: List<SlackAttachment>
)

data class SlackAttachment(
    val color: String,
    val title: String,
    val pretext: String,
    val text: String,
    val footer: String?,
)
