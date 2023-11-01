package yapp.be.domain.volunteerActivity.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.VolunteerActivityJoinQueue
import yapp.be.domain.volunteerActivity.model.VolunteerActivityWaitingQueue
import yapp.be.domain.volunteerActivity.model.dto.*
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult

interface VolunteerActivityQueryHandler {

    fun findAllVolunteerActivityByStartAtBetween(start: LocalDateTime, end: LocalDateTime): List<VolunteerActivity>

    fun findUpcomingVolunteerActivityByVolunteerId(
        volunteerId: Long
    ): VolunteerSimpleVolunteerActivityDto?
    fun findVolunteerStatByVolunteerId(
        volunteerId: Long
    ): VolunteerVolunteerActivityStatDto

    fun findVolunteerActivityDone(): List<VolunteerActivity>

    fun findShelterUserStatByShelterId(
        shelterId: Long
    ): ShelterUserVolunteerActivityStatDto
    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerActivity
    fun findById(
        id: Long,
    ): VolunteerActivity
    fun findAllShelterVolunteerActivityByShelterId(page: Int, shelterId: Long): PagedResult<ShelterSimpleVolunteerActivityDto>
    fun findAllShelterVolunteerActivityByShelterIdAndStatus(page: Int, shelterId: Long, status: VolunteerActivityStatus): PagedResult<ShelterSimpleVolunteerActivityDto>

    fun findAllVolunteerVolunteerActivityByVolunteerId(
        page: Int,
        volunteerId: Long
    ): PagedResult<VolunteerSimpleVolunteerActivityDto>
    fun findAllVolunteerVolunteerActivityByVolunteerIdAndStatus(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus
    ): PagedResult<VolunteerSimpleVolunteerActivityDto>

    fun findDetailVolunteerActivityInfoByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerActivityDto

    fun findDetailDeletedVolunteerActivityInfoByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerActivityDto

    fun findDetailVolunteerActivityInfoByIdAndShelterIdAndVolunteerId(
        id: Long,
        volunteerId: Long,
        shelterId: Long
    ): DetailVolunteerActivityDto
    fun findAllVolunteerSimpleVolunteerActivityInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>
    fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndDateRangeAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerActivityCategory?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByDateRangeAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: List<VolunteerActivityCategory>?,
        status: VolunteerActivityStatus?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun findVolunteerEventJoiningQueueByVolunteerIdAndVolunteerActivityId(
        volunteerId: Long,
        volunteerActivityId: Long
    ): VolunteerActivityJoinQueue?

    fun findVolunteerActivityWaitingQueueByVolunteerIdAndVolunteerActivityId(
        volunteerId: Long,
        volunteerActivityId: Long
    ): VolunteerActivityWaitingQueue?
}
