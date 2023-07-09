package yapp.be.domain.port.outbound

import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface VolunteerEventQueryHandler {

    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerEventDto
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
