package yapp.be.redis.streaming

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.RedisSystemException
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.stream.StreamMessageListenerContainer
import org.springframework.data.redis.stream.Subscription
import org.springframework.scheduling.annotation.Scheduled
import yapp.be.domain.model.Event
import yapp.be.redis.repository.RedisHandler
import java.net.InetAddress
import java.time.Duration

@Configuration
class EventStreamListenerConfig(
    private val eventStreamListener: EventStreamListener,
    private val redisHandler: RedisHandler,
    private val eventStreamProperties: EventStreamProperties,
) {
    private val hostname: String = InetAddress.getLocalHost().hostName

    @Bean
    fun subscription(connectionFactory: RedisConnectionFactory): Subscription? {
        createConsumerGroupIfNotExists(
            connectionFactory,
            eventStreamProperties.streamKey,
            eventStreamProperties.consumerGroup
        )

        val streamOffset: StreamOffset<String> = StreamOffset.create(
            eventStreamProperties.streamKey,
            ReadOffset.lastConsumed()
        )

        val options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions
            .builder()
            .pollTimeout(Duration.ofMillis(100))
            .batchSize(eventStreamProperties.batchSize.toInt())
            .build()

        val container = StreamMessageListenerContainer
            .create(connectionFactory, options)

        val subscription = container!!.receive(
            Consumer.from(
                eventStreamProperties.consumerGroup,
                hostname
            ),
            streamOffset,
            eventStreamListener
        )

        container.start()
        return subscription
    }

    private fun createConsumerGroupIfNotExists(
        redisConnectionFactory: RedisConnectionFactory,
        streamKey: String,
        groupName: String
    ) {
        try {
            redisConnectionFactory.connection.streamCommands()
                .xGroupCreate(
                    streamKey.toByteArray(),
                    groupName,
                    ReadOffset.from("0-0"),
                    true
                )
        } catch (e: RedisSystemException) {
            println("Consumer group already exists")
        }
    }

    @Scheduled(cron = "\${event.pending-messages.check-interval}")
    fun claimPendingMessages() {
        val pendingMessagesSummary = redisHandler.getPendingMessages(
            eventStreamProperties.streamKey,
            eventStreamProperties.consumerGroup
        )

        pendingMessagesSummary?.pendingMessagesPerConsumer?.forEach { (consumerName) ->
            if (consumerName != hostname) {
                val pendingMessages = redisHandler.getPendingMessagesPerConsumer(
                    eventStreamProperties.streamKey,
                    eventStreamProperties.consumerGroup,
                    consumerName
                )

                pendingMessages.forEach { pendingMessage ->
                    val messages = redisHandler.claimMessage(
                        eventStreamProperties.streamKey,
                        eventStreamProperties.consumerGroup,
                        hostname,
                        eventStreamProperties.pendingMessages.timeout.toLong(),
                        pendingMessage
                    )

                    messages.forEach { record ->
                        val event = Event(record.id.toString(), record.value)

                        redisHandler.acknowledge(eventStreamProperties.consumerGroup, record)
                    }
                }
            }
        }
    }
}
