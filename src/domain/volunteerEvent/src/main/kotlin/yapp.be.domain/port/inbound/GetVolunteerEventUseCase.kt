package yapp.be.domain.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterSimpleVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult

interface GetVolunteerEventUseCase {

    fun getVolunteerUpComingVolunteerEvent(
        volunteerId: Long
    ): VolunteerSimpleVolunteerEventDto?
    fun getVolunteerVolunteerEventStat(
        volunteerId: Long
    ): VolunteerVolunteerEventStatDto
    fun getShelterVolunteerEventStat(
        shelterId: Long
    ): ShelterUserVolunteerEventStatDto
    fun getVolunteerEventDone(): List<VolunteerEvent>
    fun getAllShelterVolunteerEventHistory(
        page: Int,
        shelterId: Long,
        status: VolunteerEventStatus?
    ): PagedResult<ShelterSimpleVolunteerEventDto>

    fun getAllVolunteerVolunteerEventHistory(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus?
    ): PagedResult<VolunteerSimpleVolunteerEventDto>

    fun getVolunteerEvent(
        shelterId: Long,
        volunteerEventId: Long
    ): VolunteerEvent

    fun getVolunteerEventsByDateRangeAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerEventCategory,
        status: VolunteerEventStatus
    ): List<VolunteerSimpleVolunteerEventDto>

    fun getMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getNonMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getShelterUserDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getShelterUserVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto>
    fun getMemberVolunteerEventsByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto>

    fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto>
}
