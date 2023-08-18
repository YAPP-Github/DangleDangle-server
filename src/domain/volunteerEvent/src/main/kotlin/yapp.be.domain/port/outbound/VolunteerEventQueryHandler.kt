package yapp.be.domain.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterSimpleVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult

interface VolunteerEventQueryHandler {

    fun findUpcomingVolunteerEventByVolunteerId(
        volunteerId: Long
    ): VolunteerSimpleVolunteerEventDto?
    fun findVolunteerStatByVolunteerId(
        volunteerId: Long
    ): VolunteerVolunteerEventStatDto

    fun findVolunteerEventDone(): List<VolunteerEvent>

    fun findShelterUserStatByShelterId(
        shelterId: Long
    ): ShelterUserVolunteerEventStatDto
    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerEvent
    fun findById(
        id: Long,
    ): VolunteerEvent
    fun findAllShelterVolunteerEventByShelterId(page: Int, shelterId: Long): PagedResult<ShelterSimpleVolunteerEventDto>
    fun findAllShelterVolunteerEventByShelterIdAndStatus(page: Int, shelterId: Long, status: VolunteerEventStatus): PagedResult<ShelterSimpleVolunteerEventDto>

    fun findAllVolunteerVolunteerEventByVolunteerId(
        page: Int,
        volunteerId: Long
    ): PagedResult<VolunteerSimpleVolunteerEventDto>
    fun findAllVolunteerVolunteerEventByVolunteerIdAndStatus(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus
    ): PagedResult<VolunteerSimpleVolunteerEventDto>

    fun findDetailVolunteerEventInfoByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerEventDto

    fun findDetailVolunteerEventInfoByIdAndShelterIdAndVolunteerId(
        id: Long,
        volunteerId: Long,
        shelterId: Long
    ): DetailVolunteerEventDto
    fun findAllVolunteerSimpleVolunteerEventInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto>
    fun findAllVolunteerSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto>

    fun findAllVolunteerSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndDateRangeAndStatusAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        status: VolunteerEventStatus?,
        category: VolunteerEventCategory?,
    ): List<VolunteerSimpleVolunteerEventDto>

    fun findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    ): VolunteerEventJoinQueue?

    fun findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    ): VolunteerEventWaitingQueue?
}
