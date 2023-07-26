package yapp.be.redis.streaming

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "event-kakao")
class EventStreamProperties(
    val streamKey: String,
    val consumerGroup: String,
    val pendingMessages: PendingMessagesProperties,
    val batchSize: Int,
)
class PendingMessagesProperties(
    val timeout: String
)
