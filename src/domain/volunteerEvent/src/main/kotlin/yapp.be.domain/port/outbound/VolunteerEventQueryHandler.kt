package yapp.be.domain.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterVolunteerEventStatDto
import yapp.be.domain.model.dto.SimpleVolunteerEventDto

interface VolunteerEventQueryHandler {

    fun findStatByShelterId(
        shelterId: Long
    ): ShelterVolunteerEventStatDto
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
