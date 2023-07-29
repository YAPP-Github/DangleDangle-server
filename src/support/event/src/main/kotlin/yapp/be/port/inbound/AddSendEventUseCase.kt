package yapp.be.port.inbound

import yapp.be.port.inbound.model.CreateSendEventCommand

interface AddSendEventUseCase {
    fun create(command: CreateSendEventCommand)
}
