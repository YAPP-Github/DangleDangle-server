package yapp.be.domain.port.inbound

import yapp.be.domain.port.inbound.model.CreateSendEventCommand

interface AddSendEventUseCase {
    fun create(command: CreateSendEventCommand)
}
