package yapp.be.domain.volunteerActivity.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.dto.*
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult

interface GetVolunteerActivityUseCase {

    fun getVolunteerUpComingVolunteerEvent(
        volunteerId: Long
    ): VolunteerSimpleVolunteerActivityDto?
    fun getVolunteerVolunteerEventStat(
        volunteerId: Long
    ): VolunteerVolunteerActivityStatDto
    fun getShelterVolunteerEventStat(
        shelterId: Long
    ): ShelterUserVolunteerActivityStatDto
    fun getVolunteerEventDone(): List<VolunteerActivity>
    fun getAllShelterVolunteerEventHistory(
        page: Int,
        shelterId: Long,
        status: VolunteerActivityStatus?
    ): PagedResult<ShelterSimpleVolunteerActivityDto>

    fun getAllVolunteerVolunteerEventHistory(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus?
    ): PagedResult<VolunteerSimpleVolunteerActivityDto>

    fun getVolunteerEvent(
        shelterId: Long,
        volunteerEventId: Long
    ): VolunteerActivity

    fun getVolunteerEventsByDateRangeAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerActivityCategory?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getVolunteerEventsByDateRangeAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: List<VolunteerActivityCategory>?,
        status: VolunteerActivityStatus?,
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerId: Long,
        volunteerEventId: Long
    ): DetailVolunteerActivityDto

    fun getNonMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerActivityDto

    fun getShelterUserDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerActivityDto

    fun getShelterUserVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>
    fun getMemberVolunteerEventsByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>

    fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto>
}
