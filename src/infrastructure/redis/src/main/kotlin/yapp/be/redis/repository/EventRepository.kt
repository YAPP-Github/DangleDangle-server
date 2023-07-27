package yapp.be.redis.repository

import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Event
import yapp.be.domain.port.outbound.*
import yapp.be.redis.handler.RedisHandler

@Component
class EventRepository(
    private val redisHandler: RedisHandler
) : EventCommandHandler {
    private val streamKey: String = "event-stream"
    @Transactional
    override fun saveEvents(events: List<Event>) {
        events.forEach { event ->
            val record: ObjectRecord<String, Event> = StreamRecords.newRecord()
                .ofObject<Event>(event)
                .withStreamKey(streamKey)

            redisHandler.xAdd(record)
        }
    }

    @Transactional
    override fun saveEventsPipeLined(events: List<Event>) {
        val records = events.map {
            MapRecord.create<ByteArray, ByteArray, ByteArray>(
                streamKey.toByteArray(),
                it.toMap()
            )
        }
        redisHandler.xAddPipelined(records)
    }
}
