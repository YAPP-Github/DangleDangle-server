package yapp.be.domain.port.outbound

import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface VolunteerEventQueryHandler {
    fun findAllByShelterId(shelterId: Long): List<SimpleVolunteerEventInfo>
    fun findAllWithVolunteerParticipationStatusByShelterIdAndVolunteerId(
        shelterId: Long,
        volunteerId: Long
    ): List<SimpleVolunteerEventInfo>
}
