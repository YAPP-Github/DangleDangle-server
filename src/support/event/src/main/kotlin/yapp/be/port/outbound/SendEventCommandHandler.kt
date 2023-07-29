package yapp.be.port.outbound

import yapp.be.model.SendEvent

interface SendEventCommandHandler {
    fun saveSendEventsPipeLined(sendEvents: List<SendEvent>)
}
