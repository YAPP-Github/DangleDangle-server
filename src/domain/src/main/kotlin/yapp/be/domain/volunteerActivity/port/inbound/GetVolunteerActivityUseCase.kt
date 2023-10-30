package yapp.be.domain.volunteerActivity.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.dto.*
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult

interface GetVolunteerActivityUseCase {

    fun getVolunteerUpComingVolunteerActivity(
        volunteerId: Long
    ): VolunteerSimpleVolunteerActivityDto?
    fun getVolunteerVolunteerActivityStat(
        volunteerId: Long
    ): VolunteerVolunteerActivityStatDto
    fun getShelterVolunteerActivityStat(
        shelterId: Long
    ): ShelterUserVolunteerActivityStatDto
    fun getVolunteerActivityDone(): List<VolunteerActivity>
    fun getAllShelterVolunteerActivityHistory(
        page: Int,
        shelterId: Long,
        status: VolunteerActivityStatus?
    ): PagedResult<ShelterSimpleVolunteerActivityDto>

    fun getAllVolunteerVolunteerActivityHistory(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus?
    ): PagedResult<VolunteerSimpleVolunteerActivityDto>

    fun getVolunteerActivity(
        shelterId: Long,
        volunteerActivityId: Long
    ): VolunteerActivity

    fun getVolunteerActivitiesByDateRangeAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerActivityCategory?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getVolunteerActivitiesByDateRangeAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: List<VolunteerActivityCategory>?,
        status: VolunteerActivityStatus?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getMemberDetailVolunteerActivityInfo(
        shelterId: Long,
        volunteerId: Long,
        volunteerActivityId: Long
    ): DetailVolunteerActivityDto

    fun getNonMemberDetailVolunteerActivityInfo(
        shelterId: Long,
        volunteerActivityId: Long
    ): DetailVolunteerActivityDto

    fun getShelterUserDetailVolunteerActivityInfo(
        shelterId: Long,
        volunteerActivityId: Long
    ): DetailVolunteerActivityDto

    fun getShelterUserDetailDeletedVolunteerActivityInfo(
        shelterId: Long,
        volunteerActivityId: Long
    ): DetailVolunteerActivityDto

    fun getShelterUserVolunteerActivitiesByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>
    fun getMemberVolunteerActivitiesByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getNonMemberVolunteerActivitiesByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>
}
