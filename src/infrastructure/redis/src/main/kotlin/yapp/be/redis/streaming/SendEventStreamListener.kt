package yapp.be.redis.streaming

import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service
import yapp.be.model.SendEvent
import yapp.be.redis.handler.RedisHandler

@Service
class SendEventStreamListener(
    private val redisHandler: RedisHandler,
    private val eventStreamProperties: EventStreamProperties,
) : StreamListener<String, MapRecord<String, String, String>> {
    override fun onMessage(record: MapRecord<String, String, String>) {
        val sendEvent = SendEvent(record.id.toString(), record.value)
        redisHandler.acknowledge(eventStreamProperties.consumerGroup, record)
    }
}
