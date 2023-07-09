package yapp.be.domain.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface GetVolunteerEventUseCase {

    fun getVolunteerEvent(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>
    fun getNonMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo>
}
