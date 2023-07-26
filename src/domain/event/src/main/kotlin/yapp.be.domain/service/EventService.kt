package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.Event
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.RedisTemplate

@Service
class EventService(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    private val streamKey: String = "event-stream"

    // TODO json을 읽어서 각 eventType에 맞는 행동 시작하기
    fun addEvents(event: List<Event>, pipelined: Boolean) {
        if (pipelined)
            addEventsPipelined(event)
        else
            addEvents(event)
    }

    fun addEvents(events: List<Event>) {
        events.forEach { boardingPass ->
            val record: ObjectRecord<String, Event> = StreamRecords.newRecord()
                .ofObject<Event>(boardingPass)
                .withStreamKey(streamKey)

            redisTemplate.opsForStream<Any, Any>()
                .add(record)
        }
    }

    fun addEventsPipelined(events: List<Event>) {
        val records = events.map {
            MapRecord.create<ByteArray, ByteArray, ByteArray>(
                streamKey.toByteArray(),
                it.toMap()
            )
        }

        redisTemplate.executePipelined { connection ->
            records.forEach { connection.xAdd(it) }
            null
        }
    }
}
