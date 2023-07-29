package yapp.be.apiapplication.event.listener

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus
import yapp.be.model.enums.event.EventType
import yapp.be.model.enums.event.NotificationType
import yapp.be.model.vo.*
import yapp.be.port.inbound.AddSendEventUseCase
import yapp.be.port.inbound.EditEventUseCase
import yapp.be.port.inbound.model.CreateSendEventCommand

@Component
class EventStreamListener(
    private val addSendEventUseCase: AddSendEventUseCase,
    private val editEventUseCase: EditEventUseCase,
) {
    @Async
    @EventListener
    fun processMessage(event: Event) {
        val mapper = ObjectMapper().registerKotlinModule()
        when (event.eventType) {
            EventType.UPDATE -> {
                val updateEventEntity = mapper.readValue(event.json, UpdateEventEntity::class.java)
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        updateEventEntity.volunteerIds,
                        mapOf("volunteerEventId" to updateEventEntity.volunteerEventId).toString(),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.ENABLE_JOIN -> {
                val enableJoinEventEntity = mapper.readValue(event.json, EnableJoinEventEntity::class.java)
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        enableJoinEventEntity.waitingVolunteerIds,
                        mapOf("volunteerEventId" to enableJoinEventEntity.volunteerEventId).toString(),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.VOLUNTEER_REMINDER -> {
                val volunteerReminderEventEntity = mapper.readValue(event.json, VolunteerReminderEventEntity::class.java)
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        volunteerReminderEventEntity.volunteerIds,
                        mapOf("volunteerEventId" to volunteerReminderEventEntity.volunteerEventId).toString(),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.SHELTER_REMINDER -> {
                val shelterReminderEventEntity = mapper.readValue(event.json, ShelterReminderEventEntity::class.java)
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        listOf(shelterReminderEventEntity.shelterId),
                        mapOf("volunteerEventId" to shelterReminderEventEntity.volunteerEventId).toString(),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.DELETE -> {
                val deleteEventEntity = mapper.readValue(event.json, DeleteEventEntity::class.java)
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        deleteEventEntity.volunteerIds,
                        mapOf("volunteerEventId" to deleteEventEntity.volunteerEventId).toString(),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
        }
        editEventUseCase.editStatus(event.id, EventStatus.INACTIVE)
    }
}
