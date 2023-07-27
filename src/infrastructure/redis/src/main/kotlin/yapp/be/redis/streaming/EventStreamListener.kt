package yapp.be.redis.streaming

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.stream.StreamListener
import org.springframework.stereotype.Service
import yapp.be.domain.model.Event
import yapp.be.domain.port.inbound.model.CreateSendEventCommand
import yapp.be.domain.service.AddSendEventDomainService
import yapp.be.model.enums.event.EventType
import yapp.be.model.vo.*
import yapp.be.redis.handler.RedisHandler

@Service
class EventStreamListener(
    private val redisHandler: RedisHandler,
    private val eventStreamProperties: EventStreamProperties,
    private val addSendEventDomainService: AddSendEventDomainService,
) : StreamListener<String, MapRecord<String, String, String>> {
    override fun onMessage(record: MapRecord<String, String, String>) {
        val event = Event(record.id.toString(), record.value)
        processMessage(event)
        redisHandler.acknowledge(eventStreamProperties.consumerGroup, record)
    }

    private fun processMessage(event: Event) {
        val mapper = ObjectMapper().registerKotlinModule()
        when (event.type) {
            EventType.ADD -> {
                val addEventEntity = mapper.readValue(event.json, AddEventEntity::class.java)
                addSendEventDomainService.create(
                    CreateSendEventCommand(
                        addEventEntity.likedVolunteerIds, mapOf("shelterId" to addEventEntity.shelterId).toString(), event.type
                    )
                )
            }
            EventType.PARTICIPATE -> {
                val participateEventEntity = mapper.readValue(event.json, ParticipateEventEntity::class.java)
                addSendEventDomainService.create(
                    CreateSendEventCommand(
                        listOf(participateEventEntity.volunteerId), mapOf("shelterId" to participateEventEntity.volunteerEventId).toString(), event.type
                    )
                )
            }
            EventType.WITHDRAW -> {
                val withdrawEventEntity = mapper.readValue(event.json, WithdrawEventEntity::class.java)
                addSendEventDomainService.create(
                    CreateSendEventCommand(
                        withdrawEventEntity.waitingVolunteerIds, mapOf("volunteerEventId" to withdrawEventEntity.volunteerEventId).toString(), event.type
                    )
                )
            }
        }
    }
}
