package yapp.be.domain.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.VolunteerEventJoinQueue
import yapp.be.domain.model.VolunteerEventWaitingQueue
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface VolunteerEventQueryHandler {

    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerEventDto
    fun findAllByShelterIdAndDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>
    fun findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>

    fun findVolunteerEventJoinQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId:Long
    ): VolunteerEventJoinQueue?

    fun findVolunteerEventWaitingQueueByVolunteerIdAndVolunteerEventId(
        volunteerId: Long,
        volunteerEventId:Long
    ): VolunteerEventWaitingQueue?
}
