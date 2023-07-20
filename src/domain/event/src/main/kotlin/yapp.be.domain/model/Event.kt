package yapp.be.domain.model

import yapp.be.model.enums.event.EventStatus
import java.time.LocalDateTime

abstract class Event(
    val status: EventStatus,
    val createdAt: LocalDateTime =  LocalDateTime.now()
)
