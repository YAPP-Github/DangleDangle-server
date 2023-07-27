package yapp.be.redis.streaming

import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service
import yapp.be.domain.model.Event
import yapp.be.redis.handler.RedisHandler

@Service
class EventStreamListener(
    private val redisHandler: RedisHandler,
    private val eventStreamProperties: EventStreamProperties
) : StreamListener<String, MapRecord<String, String, String>> {
    override fun onMessage(record: MapRecord<String, String, String>) {
        val event = Event(record.id.toString(), record.value)

        processMessage(event)

        redisHandler.acknowledge(eventStreamProperties.consumerGroup, record)
    }
    // TODO
    private fun processMessage(event: Event) {
    }
}
