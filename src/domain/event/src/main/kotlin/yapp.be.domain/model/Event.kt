package yapp.be.domain.model

import yapp.be.model.enums.event.EventStatus
import yapp.be.model.enums.event.EventType
import java.time.LocalDateTime

data class Event(
    val id: Long = 0,
    val volunteerId: Long,
    val volunteerEventId: Long,
    val shelterId: Long,
    val type: EventType,
    val status: EventStatus = EventStatus.BEFORE_PROCESSING,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
