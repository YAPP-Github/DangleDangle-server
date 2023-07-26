package yapp.be.domain.port.inbound

import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.model.dto.SimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto

interface GetVolunteerEventUseCase {

    fun getVolunteerVolunteerEventStat(
        volunteerId: Long
    ): VolunteerVolunteerEventStatDto
    fun getShelterVolunteerEventStat(
        shelterId: Long
    ): ShelterUserVolunteerEventStatDto

    fun getVolunteerEvent(
        shelterId: Long,
        volunteerEventId: Long
    ): VolunteerEvent

    fun getMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getNonMemberDetailVolunteerEventInfo(
        shelterId: Long,
        volunteerEventId: Long
    ): DetailVolunteerEventDto

    fun getShelterUserDetailVolunteerEventInfo(
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
