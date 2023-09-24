package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteerActivity.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerActivity.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityJoiningQueueJpaRepository
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityJpaRepository
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityWaitingQueueJpaRepository

@Component
@Transactional
class VolunteerEventCommandRepository(
    private val volunteerActivityJpaRepository: VolunteerActivityJpaRepository,
    private val volunteerActivityWaitingQueueJpaRepository: VolunteerActivityWaitingQueueJpaRepository,
    private val volunteerActivityJoiningQueueJpaRepository: VolunteerActivityJoiningQueueJpaRepository,
) : VolunteerActivityCommandHandler {

    override fun saveVolunteerActivity(volunteerEvent: VolunteerActivity): VolunteerActivity {
        val volunteerEventEntity = volunteerEvent.toEntityModel()
        return volunteerActivityJpaRepository.save(volunteerEventEntity).toDomainModel()
    }
    override fun saveAllVolunteerActivities(volunteerActivities: Collection<VolunteerActivity>): List<VolunteerActivity> {
        val volunteerEventEntities = volunteerActivities.map {
            it.toEntityModel()
        }.toList()

        return volunteerActivityJpaRepository.saveAll(volunteerEventEntities)
            .map { it.toDomainModel() }.toList()
    }

    override fun saveVolunteerEventJoinQueue(volunteerEventJoinQueue: VolunteerActivityJoinQueue): VolunteerActivityJoinQueue {
        return volunteerActivityJoiningQueueJpaRepository
            .save(volunteerEventJoinQueue.toEntityModel()).toDomainModel()
    }

    override fun saveVolunteerEventJoinQueue(volunteerEventWaitingQueue: VolunteerActivityWaitingQueue): VolunteerActivityWaitingQueue {
        return volunteerActivityWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }

    override fun saveVolunteerActivityWaitingQueue(volunteerEventWaitingQueue: VolunteerActivityWaitingQueue): VolunteerActivityWaitingQueue {
        return volunteerActivityWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }

    override fun deleteVolunteerActivityJoiningQueueByVolunteerId(volunteerEventId: Long) {
        volunteerActivityJoiningQueueJpaRepository.deleteAllByVolunteerActivityId(volunteerEventId)
    }

    override fun deleteVolunteerActivityWaitingQueueByVolunteerId(volunteerId: Long) {
        volunteerActivityWaitingQueueJpaRepository.deleteByVolunteerId(volunteerId)
    }

    override fun updateVolunteerActivity(volunteerEvent: VolunteerActivity): VolunteerActivity {
        val volunteerEventEntity = volunteerActivityJpaRepository.findByIdOrNull(volunteerEvent.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "봉사 정보를 찾을 수 없습니다.")
        volunteerEventEntity.update(volunteerEvent)

        return volunteerActivityJpaRepository.save(volunteerEventEntity).toDomainModel()
    }
    override fun deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerActivityJoiningQueueJpaRepository
            .deleteByVolunteerIdAndVolunteerActivityId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
    }

    override fun deleteVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerActivityWaitingQueueJpaRepository.deleteByVolunteerIdAndVolunteerActivityId(
            volunteerId = volunteerId,
            volunteerActivityId = volunteerEventId
        )
    }
    override fun deleteVolunteerActivityByIdAndShelterId(id: Long, shelterId: Long) {
        val volunteerEventEntity = volunteerActivityJpaRepository.findByIdAndShelterIdAndDeletedIsFalse(
            id = id,
            shelterId = shelterId
        ) ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "봉사 정보를 찾을 수 없습니다."
        )
        volunteerEventEntity.delete()

        volunteerActivityJpaRepository.save(volunteerEventEntity)
    }

    override fun deleteVolunteerActivityWaitingQueueByVolunteerActivityId(volunteerEventId: Long) {
        return volunteerActivityWaitingQueueJpaRepository.deleteAllByVolunteerActivityId(volunteerEventId)
    }
    override fun deleteVolunteerActivityJoiningQueueByVolunteerActivityId(volunteerEventId: Long) {
        return volunteerActivityJoiningQueueJpaRepository.deleteAllByVolunteerActivityId(volunteerEventId)
    }

    override fun deleteAllVolunteerEventByShelterId(shelterId: Long): List<Long> {
        val volunteerEventIds = mutableListOf<Long>()
        val volunteerEvents = volunteerActivityJpaRepository.findAllByShelterId(shelterId)
        volunteerEvents.forEach {
            volunteerActivityJpaRepository.delete(it)
            volunteerEventIds.add(it.id)
        }
        return volunteerEventIds
    }
}
