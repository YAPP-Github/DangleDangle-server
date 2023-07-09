package yapp.be.domain.port.outbound

import java.time.LocalDateTime
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface VolunteerEventQueryHandler {

    fun findByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): DetailVolunteerEventDto
    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>
    fun findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>
}
