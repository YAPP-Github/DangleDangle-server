package yapp.be.storage.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.AddVolunteerEventEvent
import yapp.be.domain.model.ParticipateVolunteerEventEvent
import yapp.be.domain.model.WithDrawVolunteerEventEvent
import yapp.be.domain.port.outbound.AddVolunteerEventEventCommandHandler
import yapp.be.domain.port.outbound.ParticipateVolunteerEventEventCommandHandler
import yapp.be.domain.port.outbound.WithDrawVolunteerEventEventCommandHandler
import yapp.be.storage.jpa.event.model.mappers.toDomainModel
import yapp.be.storage.jpa.event.model.mappers.toEntityModel
import yapp.be.storage.jpa.event.repository.AddVolunteerEventEventJpaRepository
import yapp.be.storage.jpa.event.repository.ParticipateVolunteerEventEventJpaRepository
import yapp.be.storage.jpa.event.repository.WithDrawVolunteerEventEventJpaRepository

@Component
class EventRepository(
    private val addVolunteerEventEventJpaRepository: AddVolunteerEventEventJpaRepository,
    private val participateVolunteerEventEventJpaRepository: ParticipateVolunteerEventEventJpaRepository,
    private val withDrawVolunteerEventEventJpaRepository: WithDrawVolunteerEventEventJpaRepository,
) : AddVolunteerEventEventCommandHandler, ParticipateVolunteerEventEventCommandHandler, WithDrawVolunteerEventEventCommandHandler {
    @Transactional
    override fun saveEvent(addVolunteerEventEvent: AddVolunteerEventEvent): AddVolunteerEventEvent {
        val addVolunteerEventEventEntity = addVolunteerEventEventJpaRepository.save(
            addVolunteerEventEvent.toEntityModel()
        )
        return addVolunteerEventEventEntity.toDomainModel()
    }

    @Transactional
    override fun saveEvent(participateVolunteerEventEvent: ParticipateVolunteerEventEvent): ParticipateVolunteerEventEvent {
        val participateVolunteerEventEventEntity = participateVolunteerEventEventJpaRepository.save(
            participateVolunteerEventEvent.toEntityModel()
        )
        return participateVolunteerEventEventEntity.toDomainModel()
    }

    @Transactional
    override fun saveEvent(withDrawVolunteerEventEvent: WithDrawVolunteerEventEvent): WithDrawVolunteerEventEvent {
        val withDrawVolunteerEventEventEntity = withDrawVolunteerEventEventJpaRepository.save(
            withDrawVolunteerEventEvent.toEntityModel()
        )
        return withDrawVolunteerEventEventEntity.toDomainModel()
    }
}
