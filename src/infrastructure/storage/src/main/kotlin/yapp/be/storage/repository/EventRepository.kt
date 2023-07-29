package yapp.be.storage.repository

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.exceptions.CustomException
import yapp.be.model.Event
import yapp.be.model.enums.event.EventStatus
import yapp.be.port.outbound.EventCommandHandler
import yapp.be.port.outbound.EventQueryHandler
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.event.model.mappers.toDomainModel
import yapp.be.storage.jpa.event.model.mappers.toEntityModel
import yapp.be.storage.jpa.event.repository.EventJpaRepository

@Component
class EventRepository(
    private val eventJpaRepository: EventJpaRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : EventCommandHandler, EventQueryHandler {
    @Transactional
    override fun create(event: Event): Event {
        val eventEntity = eventJpaRepository.save(event.toEntityModel())
        applicationEventPublisher.publishEvent(eventEntity.toDomainModel())
        return eventEntity.toDomainModel()
    }

    @Transactional
    override fun update(event: Event): Event {
        val eventEntity = eventJpaRepository.findByIdOrNull(event.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Event Not Found")
        eventEntity.update(event)
        eventJpaRepository.save(eventEntity)

        return eventEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findByStatus(status: EventStatus): List<Event> {
        return eventJpaRepository.findByEventStatus(status)?.map { it.toDomainModel() }
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Event Not Found")
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Event {
        return eventJpaRepository.findByIdOrNull(id)?.toDomainModel()
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Event Not Found")
    }
}
