package yapp.be.storage.repository.query

import java.time.LocalDateTime
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterSimpleVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerEventParticipantInfoDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult
import yapp.be.model.vo.Address
import yapp.be.storage.config.PAGE_SIZE
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteerevent.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinQueueJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
@Transactional(readOnly = true)
class VolunteerEventQueryRepository(
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventJoinQueueJpaRepository: VolunteerEventJoinQueueJpaRepository,
) : VolunteerEventQueryHandler {

    override fun findVolunteerStatByVolunteerId(volunteerId: Long): VolunteerVolunteerEventStatDto {
        val volunteerEventEntities = volunteerEventJpaRepository.findAllByVolunteerId(volunteerId)
        val volunteerEventEntityStatMap =
            volunteerEventEntities.groupBy { it.status }

        val volunteerEventWaitingQueueEntities = volunteerEventWaitingQueueJpaRepository.findAllByVolunteerId(volunteerId)

        return VolunteerVolunteerEventStatDto(
            done = volunteerEventEntityStatMap[VolunteerEventStatus.DONE]?.size ?: 0,
            waiting = volunteerEventWaitingQueueEntities.size,
            joining = volunteerEventEntityStatMap[VolunteerEventStatus.IN_PROGRESS]?.size ?: 0
        )
    }

    override fun findVolunteerEventDone(): List<VolunteerEvent> {
        return volunteerEventJpaRepository.findByEndAtBeforeAndStatusNot(
            LocalDateTime.now(),
            VolunteerEventStatus.DONE
        ).map { it.toDomainModel() }
    }

    override fun findShelterUserStatByShelterId(shelterId: Long): ShelterUserVolunteerEventStatDto {
        val volunteerEventEntities =
            volunteerEventJpaRepository.findAllByShelterId(shelterId)

        val volunteerEntityStatusMap = volunteerEventEntities.groupBy { it.status }

        return ShelterUserVolunteerEventStatDto(
            done = volunteerEntityStatusMap[VolunteerEventStatus.DONE]?.size ?: 0,
            inProgress = volunteerEntityStatusMap[VolunteerEventStatus.IN_PROGRESS]?.size ?: 0
        )
    }

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

    override fun findById(id: Long): VolunteerEvent {
        val volunteerEventEntity = volunteerEventJpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return volunteerEventEntity.toDomainModel()
    }

    override fun findAllShelterVolunteerEventByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ShelterSimpleVolunteerEventDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("id").descending()
        )

        val shelterVolunteerEvents = volunteerEventJpaRepository.findAllByShelterId(
            shelterId = shelterId,
            pageable = pageable,
        )

        val shelterVolunteerEventEntityMap =
            shelterVolunteerEvents
                .content
                .associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return PagedResult(
            pageNumber = shelterVolunteerEvents.number,
            pageSize = shelterVolunteerEvents.size,
            content = shelterVolunteerEventEntityMap
                .values
                .map {
                    val joinQueue = joinQueueEntityMap[it.id]
                    val waitingQueue = waitingQueueEntityMap[it.id]

                    ShelterSimpleVolunteerEventDto(
                        volunteerEventId = it.id,
                        title = it.title,
                        category = it.category,
                        startAt = it.startAt,
                        endAt = it.endAt,
                        eventStatus = it.status,
                        recruitNum = it.recruitNum,
                        participantNum = joinQueue?.size ?: 0,
                        waitingNum = waitingQueue?.size ?: 0,
                    )
                }
                .sortedBy { it.volunteerEventId }
                .toList()
        )
    }

    override fun findAllShelterVolunteerEventByShelterIdAndStatus(
        page: Int,
        shelterId: Long,
        status: VolunteerEventStatus
    ): PagedResult<ShelterSimpleVolunteerEventDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("id").descending()
        )

        val shelterVolunteerEvents = volunteerEventJpaRepository.findAllByShelterIdAndStatus(
            shelterId = shelterId,
            status = status,
            pageable = pageable,
        )

        val shelterVolunteerEventEntityMap =
            shelterVolunteerEvents
                .content
                .associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return PagedResult(
            pageNumber = shelterVolunteerEvents.number,
            pageSize = shelterVolunteerEvents.size,
            content = shelterVolunteerEventEntityMap
                .values
                .map {
                    val joinQueue = joinQueueEntityMap[it.id]
                    val waitingQueue = waitingQueueEntityMap[it.id]

                    ShelterSimpleVolunteerEventDto(
                        volunteerEventId = it.id,
                        title = it.title,
                        category = it.category,
                        startAt = it.startAt,
                        endAt = it.endAt,
                        eventStatus = it.status,
                        recruitNum = it.recruitNum,
                        participantNum = joinQueue?.size ?: 0,
                        waitingNum = waitingQueue?.size ?: 0,
                    )
                }
                .sortedBy { it.volunteerEventId }
                .toList()
        )
    }

    override fun findAllVolunteerVolunteerEventByVolunteerId(page: Int, volunteerId: Long): PagedResult<VolunteerSimpleVolunteerEventDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE
        )

        val histories = volunteerEventJpaRepository
            .findAllVolunteerEventByVolunteerId(
                volunteerId = volunteerId,
                pageable = pageable
            )

        val eventIds = histories.content.map { it.id }

        val joinParticipantMap = volunteerEventJoinQueueJpaRepository
            .findAllByVolunteerEventIdIn(eventIds)
            .groupBy { it.volunteerEventId }

        val waitingParticipantMap = volunteerEventWaitingQueueJpaRepository
            .findAllByVolunteerEventIdIn(eventIds)
            .groupBy { it.volunteerEventId }

        return PagedResult(
            pageNumber = histories.number,
            pageSize = histories.size,
            content = histories.content.map {
                VolunteerSimpleVolunteerEventDto(
                    volunteerEventId = it.id,
                    shelterName = it.shelterName,
                    title = it.title,
                    category = it.category,
                    eventStatus = it.eventStatus,
                    myParticipationStatus = UserEventParticipationStatus.valueOf(it.myParticipationStatus),
                    startAt = it.startAt,
                    endAt = it.endAt,
                    recruitNum = it.recruitNum,
                    participantNum = joinParticipantMap[it.id]?.size ?: 0,
                    waitingNum = waitingParticipantMap[it.id]?.size ?: 0
                )
            }
        )
    }

    override fun findAllVolunteerVolunteerEventByVolunteerIdAndStatus(page: Int, volunteerId: Long, status: UserEventParticipationStatus): PagedResult<VolunteerSimpleVolunteerEventDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE
        )

        return when (status) {

            UserEventParticipationStatus.DONE -> {
                val histories = volunteerEventJoinQueueJpaRepository
                    .findAllDoneVolunteerEventByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerEventJoinQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                val waitingParticipantMap = volunteerEventWaitingQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content.map {
                        VolunteerSimpleVolunteerEventDto(
                            volunteerEventId = it.id,
                            shelterName = it.shelterName,
                            title = it.title,
                            category = it.category,
                            eventStatus = it.eventStatus,
                            myParticipationStatus = UserEventParticipationStatus.DONE,
                            startAt = it.startAt,
                            endAt = it.endAt,
                            recruitNum = it.recruitNum,
                            participantNum = joinParticipantMap[it.id]?.size ?: 0,
                            waitingNum = waitingParticipantMap[it.id]?.size ?: 0

                        )
                    }
                )
            }

            UserEventParticipationStatus.JOINING -> {
                val histories = volunteerEventJoinQueueJpaRepository
                    .findAllJoinVolunteerEventByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerEventJoinQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                val waitingParticipantMap = volunteerEventWaitingQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content.map {
                        VolunteerSimpleVolunteerEventDto(
                            volunteerEventId = it.id,
                            shelterName = it.shelterName,
                            title = it.title,
                            category = it.category,
                            eventStatus = it.eventStatus,
                            myParticipationStatus = UserEventParticipationStatus.JOINING,
                            startAt = it.startAt,
                            endAt = it.endAt,
                            recruitNum = it.recruitNum,
                            participantNum = joinParticipantMap[it.id]?.size ?: 0,
                            waitingNum = waitingParticipantMap[it.id]?.size ?: 0
                        )
                    }
                )
            }
            UserEventParticipationStatus.WAITING -> {
                val histories = volunteerEventWaitingQueueJpaRepository
                    .findAllWaitingVolunteerEventByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerEventJoinQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                val waitingParticipantMap = volunteerEventWaitingQueueJpaRepository
                    .findAllByVolunteerEventIdIn(eventIds)
                    .groupBy { it.volunteerEventId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content.map {
                        VolunteerSimpleVolunteerEventDto(
                            volunteerEventId = it.id,
                            shelterName = it.shelterName,
                            title = it.title,
                            category = it.category,
                            eventStatus = it.eventStatus,
                            myParticipationStatus = UserEventParticipationStatus.WAITING,
                            startAt = it.startAt,
                            endAt = it.endAt,
                            recruitNum = it.recruitNum,
                            participantNum = joinParticipantMap[it.id]?.size ?: 0,
                            waitingNum = waitingParticipantMap[it.id]?.size ?: 0
                        )
                    }
                )
            }
            else -> PagedResult(
                0,
                PAGE_SIZE,
                emptyList()
            )
        }
    }

    override fun findDetailVolunteerEventInfoByIdAndShelterId(id: Long, shelterId: Long): DetailVolunteerEventDto {
        val volunteerEventWithMyParticipationStatus =
            volunteerEventJpaRepository
                .findWithParticipationStatusByIdAndShelterId(
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
            myParticipationStatus = UserEventParticipationStatus.NONE,
            startAt = volunteerEventWithMyParticipationStatus.startAt,
            endAt = volunteerEventWithMyParticipationStatus.endAt,
            joiningVolunteers = joiningParticipants,
            waitingVolunteers = waitingParticipants
        )
    }
    override fun findDetailVolunteerEventInfoByIdAndShelterIdAndVolunteerId(id: Long, volunteerId: Long, shelterId: Long): DetailVolunteerEventDto {
        val volunteerEventWithShelterInfo =
            volunteerEventJpaRepository
                .findWithParticipationStatusByIdAndShelterId(
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
        val waitingParticipants = if (volunteerEventWithShelterInfo.recruitNum <= joiningParticipants.size) {
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
            id = volunteerEventWithShelterInfo.id,
            shelterName = volunteerEventWithShelterInfo.shelterName,
            shelterProfileImageUrl = volunteerEventWithShelterInfo.shelterProfileImageUrl,
            title = volunteerEventWithShelterInfo.title,
            recruitNum = volunteerEventWithShelterInfo.recruitNum,
            address = Address(
                address = volunteerEventWithShelterInfo.address.address,
                addressDetail = volunteerEventWithShelterInfo.address.addressDetail,
                postalCode = volunteerEventWithShelterInfo.address.postalCode,
                latitude = volunteerEventWithShelterInfo.address.latitude,
                longitude = volunteerEventWithShelterInfo.address.longitude
            ),
            description = volunteerEventWithShelterInfo.description,
            ageLimit = volunteerEventWithShelterInfo.ageLimit,
            category = volunteerEventWithShelterInfo.category,
            eventStatus = volunteerEventWithShelterInfo.eventStatus,
            myParticipationStatus = getMyParticipationStatus(
                volunteerId = volunteerId,
                status = volunteerEventWithShelterInfo.eventStatus,
                joinQueue = joiningParticipants.map { it.id },
                waitingQueue = waitingParticipants.map { it.id }
            ),
            startAt = volunteerEventWithShelterInfo.startAt,
            endAt = volunteerEventWithShelterInfo.endAt,
            joiningVolunteers = joiningParticipants,
            waitingVolunteers = waitingParticipants
        )
    }

    override fun findAllVolunteerSimpleVolunteerEventInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto> {
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

                VolunteerSimpleVolunteerEventDto(
                    volunteerEventId = it.id,
                    shelterName = it.shelterName,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.eventStatus,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue?.size ?: 0,
                    waitingNum = waitingQueue?.size ?: 0,
                    myParticipationStatus = UserEventParticipationStatus.NONE,
                )
            }.toList()
    }

    override fun findAllVolunteerSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto> {
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

                VolunteerSimpleVolunteerEventDto(
                    volunteerEventId = it.id,
                    shelterName = it.shelterName,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.eventStatus,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue?.size ?: 0,
                    waitingNum = waitingQueue?.size ?: 0,
                    myParticipationStatus = getMyParticipationStatus(
                        volunteerId = volunteerId,
                        status = it.eventStatus,
                        joinQueue = joinQueue?.map { it.volunteerId } ?: listOf(),
                        waitingQueue = waitingQueue?.map { it.volunteerId } ?: listOf()
                    ),
                )
            }
            .sortedBy { it.volunteerEventId }
            .toList()
    }

    override fun findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventJoinQueue? {
        return volunteerEventJoinQueueJpaRepository
            .findByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )?.toDomainModel()
    }

    override fun findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(volunteerId: Long, volunteerEventId: Long): VolunteerEventWaitingQueue? {
        return volunteerEventWaitingQueueJpaRepository
            .findByVolunteerIdAndVolunteerEventId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )?.toDomainModel()
    }

    private fun getMyParticipationStatus(
        volunteerId: Long,
        status: VolunteerEventStatus,
        joinQueue: List<Long>,
        waitingQueue: List<Long>
    ): UserEventParticipationStatus {

        val isJoining = joinQueue.any { it == volunteerId }
        val isWaiting = waitingQueue.any { it == volunteerId }

        return when {
            isJoining -> when (status) {
                VolunteerEventStatus.IN_PROGRESS -> UserEventParticipationStatus.JOINING
                VolunteerEventStatus.CLOSED -> UserEventParticipationStatus.NONE
                else -> UserEventParticipationStatus.DONE
            }
            isWaiting -> UserEventParticipationStatus.WAITING
            else -> UserEventParticipationStatus.NONE
        }
    }
}
