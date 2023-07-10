package yapp.be.storage.repository

import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
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
    override fun findByIdAndShelterId(id: Long, shelterId: Long): DetailVolunteerEventDto {
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
            .map { it.nickname }.toList()
        val waitingParticipants = if (volunteerEventWithMyParticipationStatus.recruitNum <= joiningParticipants.size) {
            volunteerEventWaitingQueueJpaRepository.findAllWaitParticipantsByVolunteerEventId(volunteerEventId = id)
                .map { it.nickname }.toList()
        } else {
            listOf()
        }

        return DetailVolunteerEventDto(
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
    override fun findAllByShelterIdAndDateRange(
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
    override fun findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
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
            }.toList()
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

    override fun save(volunteerEvent: VolunteerEvent): VolunteerEvent {
        val volunteerEventEntity = volunteerEvent.toEntityModel()
        return volunteerEventJpaRepository.save(volunteerEventEntity).toDomainModel()
    }

    @Transactional
    override fun saveAll(volunteerEvents: Collection<VolunteerEvent>): List<VolunteerEvent> {
        val volunteerEventEntities = volunteerEvents.map {
            it.toEntityModel()
        }.toList()

        return volunteerEventJpaRepository.saveAll(volunteerEventEntities)
            .map { it.toDomainModel() }.toList()
    }
}
