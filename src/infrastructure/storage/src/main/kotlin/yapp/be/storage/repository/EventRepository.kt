package yapp.be.storage.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Event
import yapp.be.domain.port.outbound.*
import yapp.be.storage.jpa.event.model.mappers.toDomainModel
import yapp.be.storage.jpa.event.model.mappers.toEntityModel
import yapp.be.storage.jpa.event.repository.EventJpaRepository

@Component
class EventRepository(
    private val eventJpaRepository: EventJpaRepository,
) : EventCommandHandler, EventQueryHandler {
    @Transactional
    override fun saveEvent(event: Event): Event {
        val eventEntity = eventJpaRepository.save(
            event.toEntityModel()
        )
        return eventEntity.toDomainModel()
    }
    @Transactional(readOnly = true)
    override fun get(): List<Event> {
        return eventJpaRepository.findByEventStatusOrderByCreatedAt().map { it.toDomainModel() }
    }
}
