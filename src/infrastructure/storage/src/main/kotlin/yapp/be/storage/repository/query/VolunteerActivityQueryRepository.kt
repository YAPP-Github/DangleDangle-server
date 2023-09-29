package yapp.be.storage.repository.query

import java.time.LocalDateTime
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.domain.volunteerActivity.model.dto.*
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult
import yapp.be.model.vo.Address
import yapp.be.storage.config.PAGE_SIZE
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.volunteerActivity.model.mappers.toDomainModel
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityJoiningQueueJpaRepository
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityJpaRepository
import yapp.be.storage.jpa.volunteerActivity.repository.VolunteerActivityWaitingQueueJpaRepository

@Component
@Transactional(readOnly = true)
class VolunteerActivityQueryRepository(
    private val volunteerActivityJpaRepository: VolunteerActivityJpaRepository,
    private val volunteerActivityWaitingQueueJpaRepository: VolunteerActivityWaitingQueueJpaRepository,
    private val volunteerActivityJoiningQueueJpaRepository: VolunteerActivityJoiningQueueJpaRepository,
) : VolunteerActivityQueryHandler {
    override fun findUpcomingVolunteerActivityByVolunteerId(volunteerId: Long): VolunteerSimpleVolunteerActivityDto? {
        val upComingVolunteerActivity = volunteerActivityJpaRepository.findUpcomingVolunteerEventByVolunteerId(volunteerId)
        return upComingVolunteerActivity?.let {
            val joiningParticipantsNum = volunteerActivityJoiningQueueJpaRepository.countByVolunteerActivityId(it.id)
            val waitingParticipantsNum = volunteerActivityWaitingQueueJpaRepository.countByVolunteerActivityId(it.id)

            VolunteerSimpleVolunteerActivityDto(
                shelterId = it.shelterId,
                shelterName = it.shelterName,
                shelterProfileImageUrl = it.shelterProfileImageUrl,
                volunteerEventId = it.id,
                title = it.title,
                category = it.category,
                eventStatus = it.eventStatus,
                myParticipationStatus = UserEventParticipationStatus.JOINING,
                startAt = it.startAt,
                endAt = it.endAt,
                recruitNum = it.recruitNum,
                participantNum = joiningParticipantsNum,
                waitingNum = waitingParticipantsNum
            )
        }
    }

    override fun findVolunteerStatByVolunteerId(volunteerId: Long): VolunteerVolunteerActivityStatDto {
        val volunteerEventEntities = volunteerActivityJpaRepository.findAllByVolunteerId(volunteerId)
        val volunteerEventEntityStatMap =
            volunteerEventEntities.groupBy { it.status }

        val volunteerEventWaitingQueueEntities = volunteerActivityWaitingQueueJpaRepository.findAllByVolunteerId(volunteerId)

        return VolunteerVolunteerActivityStatDto(
            done = volunteerEventEntityStatMap[VolunteerActivityStatus.DONE]?.size ?: 0,
            waiting = volunteerEventWaitingQueueEntities.size,
            joining = volunteerEventEntityStatMap[VolunteerActivityStatus.IN_PROGRESS]?.size ?: 0
        )
    }

    override fun findVolunteerActivityDone(): List<VolunteerActivity> {
        return volunteerActivityJpaRepository.findByEndAtBeforeAndStatusNot(
            LocalDateTime.now(),
            VolunteerActivityStatus.DONE
        ).sortedBy { it.startAt }
            .map { it.toDomainModel() }
    }

    override fun findShelterUserStatByShelterId(shelterId: Long): ShelterUserVolunteerActivityStatDto {
        val volunteerEventEntities =
            volunteerActivityJpaRepository.findAllByShelterIdAndDeletedIsFalse(shelterId)

        val volunteerEntityStatusMap = volunteerEventEntities.groupBy { it.status }

        return ShelterUserVolunteerActivityStatDto(
            done = volunteerEntityStatusMap[VolunteerActivityStatus.DONE]?.size ?: 0,
            inProgress = volunteerEntityStatusMap[VolunteerActivityStatus.IN_PROGRESS]?.size ?: 0
        )
    }

    override fun findByIdAndShelterId(id: Long, shelterId: Long): VolunteerActivity {
        return volunteerActivityJpaRepository
            .findByIdAndShelterIdAndDeletedIsFalse(
                id = id,
                shelterId = shelterId
            )?.toDomainModel() ?: throw CustomException(
            type = StorageExceptionType.ENTITY_NOT_FOUND,
            message = "봉사 정보를 찾을 수 없습니다."
        )
    }

    override fun findById(id: Long): VolunteerActivity {
        val volunteerEventEntity = volunteerActivityJpaRepository.findById(id).orElseThrow { throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.") }
        return volunteerEventEntity.toDomainModel()
    }

    override fun findAllShelterVolunteerActivityByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ShelterSimpleVolunteerActivityDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("startAt")
        )

        val shelterVolunteerEvents = volunteerActivityJpaRepository.findAllByShelterIdAndDeletedIsFalse(
            shelterId = shelterId,
            pageable = pageable,
        )

        val shelterVolunteerEventEntityMap =
            shelterVolunteerEvents
                .content
                .associateBy { it.id }

        val joinQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return PagedResult(
            pageNumber = shelterVolunteerEvents.number,
            pageSize = shelterVolunteerEvents.size,
            content = shelterVolunteerEventEntityMap
                .values
                .map {
                    val joinQueue = joinQueueEntityMap[it.id]
                    val waitingQueue = waitingQueueEntityMap[it.id]

                    ShelterSimpleVolunteerActivityDto(
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
                .sortedBy { it.startAt }
                .toList()
        )
    }

    override fun findAllShelterVolunteerActivityByShelterIdAndStatus(
        page: Int,
        shelterId: Long,
        status: VolunteerActivityStatus
    ): PagedResult<ShelterSimpleVolunteerActivityDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("startAt")
        )

        val shelterVolunteerEvents = volunteerActivityJpaRepository.findAllByShelterIdAndStatusAndDeletedIsFalse(
            shelterId = shelterId,
            status = status,
            pageable = pageable,
        )

        val shelterVolunteerEventEntityMap =
            shelterVolunteerEvents
                .content
                .associateBy { it.id }

        val joinQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(shelterVolunteerEventEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return PagedResult(
            pageNumber = shelterVolunteerEvents.number,
            pageSize = shelterVolunteerEvents.size,
            content = shelterVolunteerEventEntityMap
                .values
                .map {
                    val joinQueue = joinQueueEntityMap[it.id]
                    val waitingQueue = waitingQueueEntityMap[it.id]

                    ShelterSimpleVolunteerActivityDto(
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
                .sortedBy { it.startAt }
                .toList()
        )
    }

    override fun findAllVolunteerVolunteerActivityByVolunteerId(page: Int, volunteerId: Long): PagedResult<VolunteerSimpleVolunteerActivityDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("startAt")
        )

        val histories = volunteerActivityJpaRepository
            .findAllVolunteerEventByVolunteerId(
                volunteerId = volunteerId,
                pageable = pageable
            )

        val eventIds = histories.content.map { it.id }

        val joinParticipantMap = volunteerActivityJoiningQueueJpaRepository
            .findAllByVolunteerActivityIdIn(eventIds)
            .groupBy { it.volunteerActivityId }

        val waitingParticipantMap = volunteerActivityWaitingQueueJpaRepository
            .findAllByVolunteerActivityIdIn(eventIds)
            .groupBy { it.volunteerActivityId }

        return PagedResult(
            pageNumber = histories.number,
            pageSize = histories.size,
            content = histories.content
                .sortedBy { it.startAt }
                .map {
                    VolunteerSimpleVolunteerActivityDto(
                        volunteerEventId = it.id,
                        shelterId = it.shelterId,
                        shelterName = it.shelterName,
                        shelterProfileImageUrl = it.shelterProfileImageUrl,
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

    override fun findAllVolunteerVolunteerActivityByVolunteerIdAndStatus(page: Int, volunteerId: Long, status: UserEventParticipationStatus): PagedResult<VolunteerSimpleVolunteerActivityDto> {
        val pageable = PageRequest.of(
            page,
            PAGE_SIZE,
            Sort.by("startAt")
        )

        return when (status) {

            UserEventParticipationStatus.DONE -> {
                val histories = volunteerActivityJoiningQueueJpaRepository
                    .findAllDoneVolunteerActivityByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerActivityJoiningQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                val waitingParticipantMap = volunteerActivityWaitingQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content
                        .sortedBy { it.startAt }
                        .map {
                            VolunteerSimpleVolunteerActivityDto(
                                volunteerEventId = it.id,
                                shelterId = it.shelterId,
                                shelterName = it.shelterName,
                                shelterProfileImageUrl = it.shelterProfileImageUrl,
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
                val histories = volunteerActivityJoiningQueueJpaRepository
                    .findAllJoinVolunteerActivityByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerActivityJoiningQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                val waitingParticipantMap = volunteerActivityWaitingQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content
                        .sortedBy { it.startAt }
                        .map {
                            VolunteerSimpleVolunteerActivityDto(
                                volunteerEventId = it.id,
                                shelterId = it.shelterId,
                                shelterName = it.shelterName,
                                shelterProfileImageUrl = it.shelterProfileImageUrl,
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
                val histories = volunteerActivityWaitingQueueJpaRepository
                    .findAllWaitingVolunteerActivityByVolunteerId(
                        volunteerId = volunteerId,
                        pageable = pageable
                    )

                val eventIds = histories.content.map { it.id }

                val joinParticipantMap = volunteerActivityJoiningQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                val waitingParticipantMap = volunteerActivityWaitingQueueJpaRepository
                    .findAllByVolunteerActivityIdIn(eventIds)
                    .groupBy { it.volunteerActivityId }

                PagedResult(
                    pageNumber = histories.number,
                    pageSize = histories.size,
                    content = histories.content
                        .sortedBy { it.startAt }
                        .map {
                            VolunteerSimpleVolunteerActivityDto(
                                volunteerEventId = it.id,
                                shelterId = it.shelterId,
                                shelterName = it.shelterName,
                                shelterProfileImageUrl = it.shelterProfileImageUrl,
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

    override fun findDetailVolunteerActivityInfoByIdAndShelterId(id: Long, shelterId: Long): DetailVolunteerActivityDto {
        val volunteerActivityWithMyParticipationStatus =
            volunteerActivityJpaRepository
                .findWithParticipationStatusByIdAndShelterId(
                    id = id,
                    shelterId = shelterId
                ) ?: throw CustomException(
                type = StorageExceptionType.ENTITY_NOT_FOUND,
                message = "봉사 정보를 찾을 수 없습니다."
            )

        val joiningParticipants = volunteerActivityJoiningQueueJpaRepository.findAllJoinParticipantsByVolunteerActivityId(volunteerActivityId = id)
            .map {
                VolunteerActivityParticipantInfoDto(
                    id = it.id,
                    nickName = it.nickname
                )
            }.toList()
        val waitingParticipants = if (volunteerActivityWithMyParticipationStatus.recruitNum <= joiningParticipants.size) {
            volunteerActivityWaitingQueueJpaRepository.findAllWaitParticipantsByVolunteerActivityId(volunteerActivityId = id)
                .map {
                    VolunteerActivityParticipantInfoDto(
                        id = it.id,
                        nickName = it.nickname
                    )
                }.toList()
        } else {
            listOf()
        }

        return DetailVolunteerActivityDto(
            id = volunteerActivityWithMyParticipationStatus.id,
            shelterName = volunteerActivityWithMyParticipationStatus.shelterName,
            shelterProfileImageUrl = volunteerActivityWithMyParticipationStatus.shelterProfileImageUrl,
            title = volunteerActivityWithMyParticipationStatus.title,
            recruitNum = volunteerActivityWithMyParticipationStatus.recruitNum,
            address = Address(
                address = volunteerActivityWithMyParticipationStatus.address.address,
                addressDetail = volunteerActivityWithMyParticipationStatus.address.addressDetail,
                postalCode = volunteerActivityWithMyParticipationStatus.address.postalCode,
                latitude = volunteerActivityWithMyParticipationStatus.address.latitude,
                longitude = volunteerActivityWithMyParticipationStatus.address.longitude
            ),
            description = volunteerActivityWithMyParticipationStatus.description,
            ageLimit = volunteerActivityWithMyParticipationStatus.ageLimit,
            category = volunteerActivityWithMyParticipationStatus.category,
            eventStatus = volunteerActivityWithMyParticipationStatus.eventStatus,
            myParticipationStatus = UserEventParticipationStatus.NONE,
            startAt = volunteerActivityWithMyParticipationStatus.startAt,
            endAt = volunteerActivityWithMyParticipationStatus.endAt,
            joiningVolunteers = joiningParticipants,
            waitingVolunteers = waitingParticipants
        )
    }
    override fun findDetailVolunteerActivityInfoByIdAndShelterIdAndVolunteerId(id: Long, volunteerId: Long, shelterId: Long): DetailVolunteerActivityDto {
        val volunteerActivityWithShelterInfo =
            volunteerActivityJpaRepository
                .findWithParticipationStatusByIdAndShelterId(
                    id = id,
                    shelterId = shelterId
                ) ?: throw CustomException(
                type = StorageExceptionType.ENTITY_NOT_FOUND,
                message = "봉사 정보를 찾을 수 없습니다."
            )

        val joiningParticipants = volunteerActivityJoiningQueueJpaRepository.findAllJoinParticipantsByVolunteerActivityId(volunteerActivityId = id)
            .map {
                VolunteerActivityParticipantInfoDto(
                    id = it.id,
                    nickName = it.nickname
                )
            }.toList()
        val waitingParticipants = if (volunteerActivityWithShelterInfo.recruitNum <= joiningParticipants.size) {
            volunteerActivityWaitingQueueJpaRepository.findAllWaitParticipantsByVolunteerActivityId(volunteerActivityId = id)
                .map {
                    VolunteerActivityParticipantInfoDto(
                        id = it.id,
                        nickName = it.nickname
                    )
                }.toList()
        } else {
            listOf()
        }

        return DetailVolunteerActivityDto(
            id = volunteerActivityWithShelterInfo.id,
            shelterName = volunteerActivityWithShelterInfo.shelterName,
            shelterProfileImageUrl = volunteerActivityWithShelterInfo.shelterProfileImageUrl,
            title = volunteerActivityWithShelterInfo.title,
            recruitNum = volunteerActivityWithShelterInfo.recruitNum,
            address = Address(
                address = volunteerActivityWithShelterInfo.address.address,
                addressDetail = volunteerActivityWithShelterInfo.address.addressDetail,
                postalCode = volunteerActivityWithShelterInfo.address.postalCode,
                latitude = volunteerActivityWithShelterInfo.address.latitude,
                longitude = volunteerActivityWithShelterInfo.address.longitude
            ),
            description = volunteerActivityWithShelterInfo.description,
            ageLimit = volunteerActivityWithShelterInfo.ageLimit,
            category = volunteerActivityWithShelterInfo.category,
            eventStatus = volunteerActivityWithShelterInfo.eventStatus,
            myParticipationStatus = getMyParticipationStatus(
                volunteerId = volunteerId,
                status = volunteerActivityWithShelterInfo.eventStatus,
                joinQueue = joiningParticipants.map { it.id },
                waitingQueue = waitingParticipants.map { it.id }
            ),
            startAt = volunteerActivityWithShelterInfo.startAt,
            endAt = volunteerActivityWithShelterInfo.endAt,
            joiningVolunteers = joiningParticipants,
            waitingVolunteers = waitingParticipants
        )
    }

    override fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndDateRangeAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerActivityCategory?,
    ): List<VolunteerSimpleVolunteerActivityDto> {
        val volunteerActivityEntityMap =
            volunteerActivityJpaRepository.findAllByShelterIdAndYearAndMonthAndCategory(
                shelterId = shelterId,
                from = from,
                to = to,
                category = category,
            ).associateBy { it.id }

        val joiningQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return volunteerActivityEntityMap
            .values
            .map {
                val joinQueue = joiningQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                VolunteerSimpleVolunteerActivityDto(
                    shelterId = shelterId,
                    volunteerEventId = it.id,
                    shelterName = it.shelterName,
                    shelterProfileImageUrl = it.shelterProfileImageUrl,
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
            }
            .sortedBy { it.startAt }
            .toList()
    }

    override fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByDateRangeAndCategoryAndStatus(shelterId: Long, from: LocalDateTime, to: LocalDateTime, category: List<VolunteerActivityCategory>?, status: VolunteerActivityStatus?): List<VolunteerSimpleVolunteerActivityDto> {
        val volunteerActivityEntityMap =
            volunteerActivityJpaRepository.findAllByShelterIdAndYearAndMonthAndCategoryAndStatus(
                shelterId = shelterId,
                from = from,
                to = to,
                category = category,
                status = status,
            ).associateBy { it.id }

        val joiningQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return volunteerActivityEntityMap
            .values
            .map {
                val joinQueue = joiningQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                VolunteerSimpleVolunteerActivityDto(
                    shelterId = shelterId,
                    volunteerEventId = it.id,
                    shelterName = it.shelterName,
                    shelterProfileImageUrl = it.shelterProfileImageUrl,
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
            }
            .sortedBy { it.startAt }
            .toList()
    }

    override fun findAllVolunteerSimpleVolunteerActivityInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto> {
        val volunteerActivityEntityMap =
            volunteerActivityJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                from = from,
                to = to
            ).associateBy { it.id }

        val joiningQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return volunteerActivityEntityMap
            .values
            .map {
                val joinQueue = joiningQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                VolunteerSimpleVolunteerActivityDto(
                    volunteerEventId = it.id,
                    shelterId = it.shelterId,
                    shelterName = it.shelterName,
                    shelterProfileImageUrl = it.shelterProfileImageUrl,
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
            }
            .sortedBy { it.startAt }
            .toList()
    }

    override fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto> {
        val volunteerActivityEntityMap =
            volunteerActivityJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                from = from,
                to = to
            ).associateBy { it.id }

        val joiningQueueEntityMap =
            volunteerActivityJoiningQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        val waitingQueueEntityMap =
            volunteerActivityWaitingQueueJpaRepository
                .findAllByVolunteerActivityIdIn(volunteerActivityEntityMap.keys)
                .groupBy { it.volunteerActivityId }

        return volunteerActivityEntityMap
            .values
            .map {
                val joinQueue = joiningQueueEntityMap[it.id]
                val waitingQueue = waitingQueueEntityMap[it.id]

                VolunteerSimpleVolunteerActivityDto(
                    volunteerEventId = it.id,
                    shelterId = it.shelterId,
                    shelterName = it.shelterName,
                    shelterProfileImageUrl = it.shelterProfileImageUrl,
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
            .sortedBy { it.startAt }
            .toList()
    }

    override fun findVolunteerEventJoiningQueueByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerEventId: Long): VolunteerActivityJoinQueue? {
        return volunteerActivityJoiningQueueJpaRepository
            .findByVolunteerIdAndVolunteerActivityId(
                volunteerId = volunteerId,
                volunteerEventId = volunteerEventId
            )?.toDomainModel()
    }

    override fun findVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(volunteerId: Long, volunteerEventId: Long): VolunteerActivityWaitingQueue? {
        return volunteerActivityWaitingQueueJpaRepository
            .findByVolunteerIdAndVolunteerActivityId(
                volunteerId = volunteerId,
                volunteerActivityId = volunteerEventId
            )?.toDomainModel()
    }

    private fun getMyParticipationStatus(
        volunteerId: Long,
        status: VolunteerActivityStatus,
        joinQueue: List<Long>,
        waitingQueue: List<Long>
    ): UserEventParticipationStatus {

        val isJoining = joinQueue.any { it == volunteerId }
        val isWaiting = waitingQueue.any { it == volunteerId }

        return when {
            isJoining -> when (status) {
                VolunteerActivityStatus.IN_PROGRESS -> UserEventParticipationStatus.JOINING
                VolunteerActivityStatus.CLOSED -> UserEventParticipationStatus.NONE
                else -> UserEventParticipationStatus.DONE
            }
            isWaiting -> UserEventParticipationStatus.WAITING
            else -> UserEventParticipationStatus.NONE
        }
    }
}
