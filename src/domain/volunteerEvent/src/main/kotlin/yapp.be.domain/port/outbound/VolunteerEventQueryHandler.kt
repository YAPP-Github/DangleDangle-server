package yapp.be.domain.port.outbound

import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface VolunteerEventQueryHandler {
    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        year: Int,
        month: Int
    ): List<SimpleVolunteerEventInfo>
    fun findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        year: Int,
        month: Int
    ): List<SimpleVolunteerEventInfo>
}
