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
import yapp.be.port.inbound.model.EventInfo
import yapp.be.port.inbound.model.SenderInfo

@Component
class EventStreamListener(
    private val addSendEventUseCase: AddSendEventUseCase,
    private val editEventUseCase: EditEventUseCase,
) {
    @Async
    @EventListener
    fun processMessage(event: Event) {
        val mapper = ObjectMapper().registerKotlinModule()
        val senders = mutableListOf<SenderInfo>()
        when (event.eventType) {
            EventType.UPDATE -> {
                val updateEventEntity = mapper.readValue(event.json, UpdateEventEntity::class.java)
                updateEventEntity.volunteer.forEach {
                    senders.add(SenderInfo(it.id, it.nickName))
                }
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        senders,
                        EventInfo(updateEventEntity.volunteerEvent.name, updateEventEntity.volunteerEvent.startAt, updateEventEntity.volunteerEvent.endAt),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.ENABLE_JOIN -> {
                val enableJoinEventEntity = mapper.readValue(event.json, EnableJoinEventEntity::class.java)
                enableJoinEventEntity.waitingVolunteers.forEach {
                    senders.add(SenderInfo(it.id, it.nickName))
                }
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        senders,
                        EventInfo(enableJoinEventEntity.volunteerEvent.name, enableJoinEventEntity.volunteerEvent.startAt, enableJoinEventEntity.volunteerEvent.endAt),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.VOLUNTEER_REMINDER -> {
                val volunteerReminderEventEntity = mapper.readValue(event.json, VolunteerReminderEventEntity::class.java)
                volunteerReminderEventEntity.volunteer.forEach {
                    senders.add(SenderInfo(it.id, it.nickName))
                }
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        senders,
                        EventInfo(volunteerReminderEventEntity.volunteerEvent.name, volunteerReminderEventEntity.volunteerEvent.startAt, volunteerReminderEventEntity.volunteerEvent.endAt),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.SHELTER_REMINDER -> {
                val shelterReminderEventEntity = mapper.readValue(event.json, ShelterReminderEventEntity::class.java)
                senders.add(SenderInfo(shelterReminderEventEntity.volunteerEvent.id, shelterReminderEventEntity.volunteerEvent.name))
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        senders,
                        EventInfo(shelterReminderEventEntity.volunteerEvent.name, shelterReminderEventEntity.volunteerEvent.startAt, shelterReminderEventEntity.volunteerEvent.endAt),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
            EventType.DELETE -> {
                val deleteEventEntity = mapper.readValue(event.json, DeleteEventEntity::class.java)
                deleteEventEntity.volunteer.forEach {
                    senders.add(SenderInfo(it.id, it.nickName))
                }
                addSendEventUseCase.create(
                    CreateSendEventCommand(
                        senders,
                        EventInfo(deleteEventEntity.volunteerEvent.name, deleteEventEntity.volunteerEvent.startAt, deleteEventEntity.volunteerEvent.endAt),
                        event.eventType,
                        NotificationType.KAKAOTALK
                    )
                )
            }
        }
        editEventUseCase.editStatus(event.id, EventStatus.INACTIVE)
    }
}
