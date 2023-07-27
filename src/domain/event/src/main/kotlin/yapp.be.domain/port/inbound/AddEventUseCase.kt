package yapp.be.domain.port.inbound

import yapp.be.domain.port.inbound.model.CreateEventCommand

interface AddEventUseCase {
    fun create(command: CreateEventCommand)
}
