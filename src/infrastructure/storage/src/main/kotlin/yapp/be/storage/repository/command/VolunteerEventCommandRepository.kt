package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteerevent.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerevent.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinQueueJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
@Transactional
class VolunteerEventCommandRepository(
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventJoinQueueJpaRepository: VolunteerEventJoinQueueJpaRepository,
) : VolunteerEventCommandHandler {

    override fun saveVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent {
        val volunteerEventEntity = volunteerEvent.toEntityModel()
        return volunteerEventJpaRepository.save(volunteerEventEntity).toDomainModel()
    }
    override fun saveAllVolunteerEvents(volunteerEvents: Collection<VolunteerEvent>): List<VolunteerEvent> {
        val volunteerEventEntities = volunteerEvents.map {
            it.toEntityModel()
        }.toList()

        return volunteerEventJpaRepository.saveAll(volunteerEventEntities)
            .map { it.toDomainModel() }.toList()
    }

    override fun saveVolunteerEventJoinQueue(volunteerEventJoinQueue: VolunteerEventJoinQueue): VolunteerEventJoinQueue {
        return volunteerEventJoinQueueJpaRepository
            .save(volunteerEventJoinQueue.toEntityModel()).toDomainModel()
    }

    override fun saveVolunteerEventJoinQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue {
        return volunteerEventWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }

    override fun saveVolunteerEventWaitingQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue {
        return volunteerEventWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }
    override fun updateVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent {
        val volunteerEventEntity = volunteerEventJpaRepository.findByIdOrNull(volunteerEvent.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "봉사 정보를 찾을 수 없습니다.")
        volunteerEventEntity.update(volunteerEvent)

        return volunteerEventJpaRepository.save(volunteerEventEntity).toDomainModel()
    }
    override fun deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerEventJoinQueueJpaRepository
            .deleteByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
    }

    override fun deleteVolunteerEventWaitingQueueByVolunteerEventId(volunteerEventId: Long) {
        return volunteerEventWaitingQueueJpaRepository.deleteByVolunteerEventId(volunteerEventId)
    }

    override fun deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerEventWaitingQueueJpaRepository.deleteByVolunteerIdAndVolunteerEventId(
            volunteerId = volunteerId,
            volunteerEventId = volunteerEventId
        )
    }
    override fun deleteVolunteerEventWaitingQueueByVolunteerEventId(volunteerEventId: Long) {
        return volunteerEventWaitingQueueJpaRepository.deleteAllByVolunteerEventId(volunteerEventId)
    }
    override fun deleteVolunteerEventJoinQueueByVolunteerEventId(volunteerEventId: Long) {
        return volunteerEventJoinQueueJpaRepository.deleteAllByVolunteerEventId(volunteerEventId)
    }
    override fun deleteVolunteerEventByIdAndShelterId(id: Long, shelterId: Long) {
        val volunteerEventEntity = volunteerEventJpaRepository.findByIdAndShelterIdAndDeletedIsFalse(
            id = id,
            shelterId = shelterId
        ) ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "봉사 정보를 찾을 수 없습니다."
        )
        volunteerEventEntity.delete()

        volunteerEventJpaRepository.save(volunteerEventEntity)
    }
    override fun deleteAllVolunteerEventByShelterId(shelterId: Long): List<Long> {
        val volunteerEventIds = mutableListOf<Long>()
        val volunteerEvents = volunteerEventJpaRepository.findAllByShelterId(shelterId)
        volunteerEvents.forEach {
            volunteerEventJpaRepository.delete(it)
            volunteerEventIds.add(it.id)
        }
        return volunteerEventIds
    }
}
