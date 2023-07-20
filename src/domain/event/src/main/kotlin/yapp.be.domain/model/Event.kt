package yapp.be.domain.model

import yapp.be.model.enums.event.EventStatus
import java.time.LocalDateTime

abstract class Event(
    val status: EventStatus = EventStatus.BEFORE_PROCESSING,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
