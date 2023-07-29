package yapp.be.port.outbound

import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus

interface EventQueryHandler {
    fun findByStatus(status: EventStatus): List<Event>
    fun findById(id: Long): Event
}
