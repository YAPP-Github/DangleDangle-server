package yapp.be.domain.port.outbound

import yapp.be.domain.model.Event

interface EventQueryHandler {
    fun get(): List<Event>
}
