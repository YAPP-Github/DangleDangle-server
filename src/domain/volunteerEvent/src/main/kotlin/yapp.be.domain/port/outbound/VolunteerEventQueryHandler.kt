package yapp.be.domain.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.model.dto.SimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto

interface VolunteerEventQueryHandler {

    fun findVolunteerStatByVolunteerId(
        volunteerId: Long
    ): VolunteerVolunteerEventStatDto

    fun findShelterUserStatByShelterId(
        shelterId: Long
    ): ShelterUserVolunteerEventStatDto
    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerEvent

    fun findDetailVolunteerEventInfoByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerEventDto

    fun findDetailVolunteerEventInfoByIdAndShelterIdAndVolunteerId(
        id: Long,
        volunteerId: Long,
        shelterId: Long
    ): DetailVolunteerEventDto
    fun findAllSimpleVolunteerEventInfosByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto>
    fun findAllSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto>

    fun findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    ): VolunteerEventJoinQueue?

    fun findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId: Long
    ): VolunteerEventWaitingQueue?
}
