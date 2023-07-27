package yapp.be.domain.port.outbound

import yapp.be.domain.model.SendEvent

interface SendEventCommandHandler {
    fun saveSendEventsPipeLined(sendEvent: List<SendEvent>)
}
