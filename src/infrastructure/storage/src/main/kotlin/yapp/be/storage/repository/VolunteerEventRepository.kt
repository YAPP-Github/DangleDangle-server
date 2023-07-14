package yapp.be.storage.repository

import java.time.LocalDateTime
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.model.dto.VolunteerEventParticipantInfoDto
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.exceptions.CustomException
import yapp.be.model.vo.Address
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerevent.model.mappers.toEntityModel
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinQueueJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
class VolunteerEventRepository(
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventJoinQueueJpaRepository: VolunteerEventJoinQueueJpaRepository,
) : VolunteerEventQueryHandler, VolunteerEventCommandHandler {

    @Transactional(readOnly = true)
    override fun findByIdAndShelterId(id: Long, shelterId: Long): VolunteerEvent {
        return volunteerEventJpaRepository
            .findByIdAndShelterIdAndDeletedIsFalse(
                id = id,
                shelterId = shelterId
            )?.toDomainModel() ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "봉사 정보를 찾을 수 없습니다."
        )
    }

    @Transactional(readOnly = true)
    override fun findDetailVolunteerEventInfoByIdAndShelterId(id: Long, shelterId: Long): DetailVolunteerEventDto {
        val volunteerEventWithMyParticipationStatus =
            volunteerEventJpaRepository
                .findByIdAndShelterIdWithMyParticipationStatus(
                    id = id,
                    shelterId = shelterId
                ) ?: throw CustomException(
                type = StorageExceptionType.ENTITY_NOT_FOUND,
                message = "봉사 정보를 찾을 수 없습니다."
            )

        val joiningParticipants = volunteerEventJoinQueueJpaRepository.findAllJoinParticipantsByVolunteerEventId(volunteerEventId = id)
            .map {
                VolunteerEventParticipantInfoDto(
                    id = it.id,
                    nickName = it.nickname
                )
            }.toList()
        val waitingParticipants = if (volunteerEventWithMyParticipationStatus.recruitNum <= joiningParticipants.size) {
            volunteerEventWaitingQueueJpaRepository.findAllWaitParticipantsByVolunteerEventId(volunteerEventId = id)
                .map {
                    VolunteerEventParticipantInfoDto(
                        id = it.id,
                        nickName = it.nickname
                    )
                }.toList()
        } else {
            listOf()
        }

        return DetailVolunteerEventDto(
            id = volunteerEventWithMyParticipationStatus.id,
            shelterName = volunteerEventWithMyParticipationStatus.shelterName,
            shelterProfileImageUrl = volunteerEventWithMyParticipationStatus.shelterProfileImageUrl,
            title = volunteerEventWithMyParticipationStatus.title,
            recruitNum = volunteerEventWithMyParticipationStatus.recruitNum,
            address = Address(
                address = volunteerEventWithMyParticipationStatus.address.address,
                addressDetail = volunteerEventWithMyParticipationStatus.address.addressDetail,
                postalCode = volunteerEventWithMyParticipationStatus.address.postalCode,
                latitude = volunteerEventWithMyParticipationStatus.address.latitude,
                longitude = volunteerEventWithMyParticipationStatus.address.longitude
            ),
            description = volunteerEventWithMyParticipationStatus.description,
            ageLimit = volunteerEventWithMyParticipationStatus.ageLimit,
            category = volunteerEventWithMyParticipationStatus.category,
            eventStatus = volunteerEventWithMyParticipationStatus.eventStatus,
            myParticipationStatus = when {
                volunteerEventWithMyParticipationStatus.isJoining -> UserEventParticipationStatus.JOINING
                volunteerEventWithMyParticipationStatus.isWaiting -> UserEventParticipationStatus.WAITING
                else -> UserEventParticipationStatus.NONE
            },
            startAt = volunteerEventWithMyParticipationStatus.startAt,
            endAt = volunteerEventWithMyParticipationStatus.endAt,
            joiningVolunteers = joiningParticipants,
            waitingVolunteers = waitingParticipants
        )
    }

    @Transactional(readOnly = true)
    override fun findAllSimpleVolunteerEventInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo> {
        val volunteerEventEntityMap =
            volunteerEventJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                from = from,
                to = to
            ).associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return volunteerEventEntityMap
            .values
            .map {
                val joinQueue = joinQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                SimpleVolunteerEventInfo(
                    volunteerEventId = it.id,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.status,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue?.size ?: 0,
                    waitingNum = waitingQueue?.size ?: 0,
                    myParticipationStatus = UserEventParticipationStatus.NONE,
                )
            }.toList()
    }

    @Transactional(readOnly = true)
    override fun findAllSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo> {
        val volunteerEventEntityMap =
            volunteerEventJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                from = from,
                to = to
            ).associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return volunteerEventEntityMap
            .values
            .map {
                val joinQueue = joinQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                SimpleVolunteerEventInfo(
                    volunteerEventId = it.id,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.status,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue?.size ?: 0,
                    waitingNum = waitingQueue?.size ?: 0,
                    myParticipationStatus = getMyParticipationStatus(
                        volunteerId = volunteerId,
                        joinQueue = joinQueue ?: listOf(),
                        waitingQueue = waitingQueue ?: listOf()
                    ),
                )
            }
            .sortedBy { it.volunteerEventId }
            .toList()
    }

    @Transactional(readOnly = true)
    override fun findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventJoinQueue? {
        return volunteerEventJoinQueueJpaRepository
            .findByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )?.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventWaitingQueue? {
        return volunteerEventWaitingQueueJpaRepository
            .findByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )?.toDomainModel()
    }

    private fun getMyParticipationStatus(
        volunteerId: Long,
        joinQueue: List<VolunteerEventJoinQueueEntity>,
        waitingQueue: List<VolunteerEventWaitingQueueEntity>
    ): UserEventParticipationStatus {

        return if (joinQueue.any { it.volunteerId == volunteerId })
            UserEventParticipationStatus.JOINING
        else if (waitingQueue.any { it.volunteerId == volunteerId })
            UserEventParticipationStatus.WAITING
        else
            UserEventParticipationStatus.NONE
    }

    @Transactional
    override fun saveVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent {
        val volunteerEventEntity = volunteerEvent.toEntityModel()
        return volunteerEventJpaRepository.save(volunteerEventEntity).toDomainModel()
    }

    @Transactional
    override fun saveAllVolunteerEvents(volunteerEvents: Collection<VolunteerEvent>): List<VolunteerEvent> {
        val volunteerEventEntities = volunteerEvents.map {
            it.toEntityModel()
        }.toList()

        return volunteerEventJpaRepository.saveAll(volunteerEventEntities)
            .map { it.toDomainModel() }.toList()
    }

    @Transactional
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

    @Transactional
    override fun saveVolunteerEventJoinQueue(volunteerEventJoinQueue: VolunteerEventJoinQueue): VolunteerEventJoinQueue {
        return volunteerEventJoinQueueJpaRepository
            .save(volunteerEventJoinQueue.toEntityModel()).toDomainModel()
    }

    @Transactional
    override fun saveVolunteerEventJoinQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue {
        return volunteerEventWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }

    @Transactional
    override fun saveVolunteerEventWaitingQueue(volunteerEventWaitingQueue: VolunteerEventWaitingQueue): VolunteerEventWaitingQueue {
        return volunteerEventWaitingQueueJpaRepository
            .save(volunteerEventWaitingQueue.toEntityModel()).toDomainModel()
    }

    @Transactional
    override fun updateVolunteerEvent(volunteerEvent: VolunteerEvent): VolunteerEvent {
        val volunteerEventEntity = volunteerEventJpaRepository.findByIdOrNull(volunteerEvent.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "봉사 정보를 찾을 수 없습니다.")
        volunteerEventEntity.update(volunteerEvent)

        return volunteerEventJpaRepository.save(volunteerEventEntity).toDomainModel()
    }

    @Transactional
    override fun deleteVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerEventJoinQueueJpaRepository
            .deleteByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )
    }

    @Transactional
    override fun deleteVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long) {
        return volunteerEventWaitingQueueJpaRepository.deleteByVolunteerIdAndVolunteerEventId(
            volunteerId = volunteerId,
            volunteerEventId = volunteerEventId
        )
    }
}
