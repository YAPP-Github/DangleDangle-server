package yapp.be.redis.streaming

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service
import yapp.be.domain.model.Event
import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType
import yapp.be.model.vo.*
import yapp.be.redis.handler.RedisHandler

@Service
class EventStreamListener(
    private val redisHandler: RedisHandler,
    private val eventStreamProperties: EventStreamProperties
) : StreamListener<String, MapRecord<String, String, String>> {
    val streamKey = "kakaotalk"
    override fun onMessage(record: MapRecord<String, String, String>) {
        val event = Event(record.id.toString(), record.value)

        processMessage(event)

        redisHandler.acknowledge(eventStreamProperties.consumerGroup, record)
    }

    private fun processMessage(event: Event) {
        when (event.type) {
            // 봉사이벤트 추가 -> 보호소를 like해놓은 사람들에게 알림
            EventType.ADD -> addEvent(event.json)
            // 봉사이벤트 참가 -> 이벤트 참여한 사람에게 알림
            EventType.PARTICIPATE -> participateEvent(event.json)
            // 봉사 이벤트 취소 -> 이벤트 대기하는 사람에게 알림
            EventType.WITHDRAW -> withdrawEvent(event.json)
        }
    }

    private fun addEvent(json: String) {
        val mapper = ObjectMapper().registerKotlinModule()
        val addEventEntity = mapper.readValue(json, AddEventEntity::class.java)
        val sendEvents = addEventEntity.likedVolunteerIds.map {
            SendEvent(EventType.ADD, it, NotificationType.KAKAOTALK, mapOf("shelterId" to addEventEntity.shelterId).toString())
        }.toMutableList()
        val records = sendEvents.map {
            MapRecord.create<ByteArray, ByteArray, ByteArray>(
                streamKey.toByteArray(),
                it.toMap()
            )
        }
        redisHandler.xAddPipelined(records)
    }

    private fun participateEvent(json: String) {
        val mapper = ObjectMapper().registerKotlinModule()
        val participateEventEntity = mapper.readValue(json, ParticipateEventEntity::class.java)
        val sendEvent = SendEvent(EventType.PARTICIPATE, participateEventEntity.volunteerId, NotificationType.KAKAOTALK, mapOf("shelterId" to participateEventEntity.volunteerEventId).toString())
        val record = MapRecord.create<ByteArray, ByteArray, ByteArray> (
            streamKey.toByteArray(),
            sendEvent.toMap()
        )
        redisHandler.xAddPipelined(listOf(record))
    }

    private fun withdrawEvent(json: String) {
        val mapper = ObjectMapper().registerKotlinModule()
        val withdrawEventEntity = mapper.readValue(json, WithdrawEventEntity::class.java)
        val sendEvents = withdrawEventEntity.waitingVolunteerIds.map {
            SendEvent(EventType.ADD, it, NotificationType.KAKAOTALK, mapOf("volunteerEventId" to withdrawEventEntity.volunteerEventId).toString())
        }.toMutableList()
        val records = sendEvents.map {
            MapRecord.create<ByteArray, ByteArray, ByteArray>(
                streamKey.toByteArray(),
                it.toMap()
            )
        }
        redisHandler.xAddPipelined(records)
    }
}
