package yapp.be.redis.streaming

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "event")
data class EventStreamProperties(
    val streamKey: String,
    val consumerGroup: String,
    val pendingMessages: PendingMessagesProperties,
    val batchSize: Long,
) {
    data class PendingMessagesProperties(
        val timeout: Long
    )
}
