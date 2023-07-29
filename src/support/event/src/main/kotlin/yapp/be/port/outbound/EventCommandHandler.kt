package yapp.be.port.outbound

import yapp.be.model.Event

interface EventCommandHandler {
    fun create(event: Event): Event
    fun update(event: Event): Event
}
