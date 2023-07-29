package yapp.be.port.inbound

import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus

interface EditEventUseCase {
    fun editStatus(
        id: Long,
        status: EventStatus,
    ): Event
}
