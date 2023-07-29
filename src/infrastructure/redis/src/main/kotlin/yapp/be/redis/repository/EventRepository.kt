package yapp.be.redis.repository

import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.SendEvent
import yapp.be.port.outbound.SendEventCommandHandler
import yapp.be.redis.handler.RedisHandler

@Component
class EventRepository(
    private val redisHandler: RedisHandler,
) : SendEventCommandHandler {
    private val newStreamKey: String = "new-event-stream"

    @Transactional
    override fun saveSendEventsPipeLined(sendEvents: List<SendEvent>) {
        val records = sendEvents.map {
            MapRecord.create<ByteArray, ByteArray, ByteArray>(
                newStreamKey.toByteArray(),
                it.toMap()
            )
        }
        redisHandler.xAddPipelined(records)
    }
}
