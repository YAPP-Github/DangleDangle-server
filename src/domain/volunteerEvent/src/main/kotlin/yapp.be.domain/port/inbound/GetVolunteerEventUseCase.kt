package yapp.be.domain.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventDto

interface GetVolunteerEventUseCase {

    fun getVolunteerEvent(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getShelterUserVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto>
    fun getMemberVolunteerEventsByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto>
    fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto>
}
