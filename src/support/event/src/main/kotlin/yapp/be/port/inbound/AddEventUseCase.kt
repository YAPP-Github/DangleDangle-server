package yapp.be.port.inbound

import yapp.be.model.Event
import yapp.be.port.inbound.model.CreateEventCommand

interface AddEventUseCase {
    fun create(command: CreateEventCommand): Event
}