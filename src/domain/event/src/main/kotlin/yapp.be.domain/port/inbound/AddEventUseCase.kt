package yapp.be.domain.port.inbound

import yapp.be.domain.model.Event
import yapp.be.model.enums.event.EventType

interface AddEventUseCase {
    fun addEvent(
        recordId: String,
        json: String,
        type: EventType,
    ): Event
}
