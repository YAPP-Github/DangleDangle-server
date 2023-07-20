package yapp.be.domain.port.outbound

import yapp.be.domain.model.Event


interface EventStoreHandler {
    fun saveEvents(event: Event)
}
