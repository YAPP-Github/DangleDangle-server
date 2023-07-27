package yapp.be.domain.port.outbound

import yapp.be.domain.model.Event

interface EventCommandHandler {
    fun saveEvent(event: Event)
}
