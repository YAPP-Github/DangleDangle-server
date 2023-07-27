package yapp.be.domain.port.inbound

import yapp.be.domain.port.inbound.model.CreateEventsCommand

interface AddEventUseCase {
    fun create(command: CreateEventsCommand)
}
