package yapp.be.domain.port.inbound

import yapp.be.domain.model.Event

interface AddEventUseCase {
    fun addEvent(
        event: Event
    ): Event
}
