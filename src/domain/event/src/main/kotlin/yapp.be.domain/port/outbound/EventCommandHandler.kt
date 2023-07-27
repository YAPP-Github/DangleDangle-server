package yapp.be.domain.port.outbound

import yapp.be.domain.model.Event

interface EventCommandHandler {
    fun saveEvents(event: List<Event>)
    fun saveEventsPipeLined(event: List<Event>)
}
