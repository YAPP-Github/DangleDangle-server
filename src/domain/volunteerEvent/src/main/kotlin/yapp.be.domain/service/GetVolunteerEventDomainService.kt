package yapp.be.domain.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterVolunteerEventStatDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.model.dto.SimpleVolunteerEventDto
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler

@Service
class GetVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler
) : GetVolunteerEventUseCase {

    @Transactional(readOnly = true)
    override fun getShelterVolunteerEventStat(shelterId: Long): ShelterVolunteerEventStatDto {
        return volunteerEventQueryHandler.findStatByShelterId(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEvent(shelterId: Long, volunteerEventId: Long): VolunteerEvent {
        return volunteerEventQueryHandler
            .findByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getMemberDetailVolunteerEventInfo(shelterId: Long, volunteerId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler.findDetailVolunteerEventInfoByIdAndShelterIdAndVolunteerId(
            id = volunteerEventId,
            volunteerId = volunteerId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler
            .findDetailVolunteerEventInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler
            .findDetailVolunteerEventInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserVolunteerEventsByDateRange(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<SimpleVolunteerEventDto> {
        return volunteerEventQueryHandler
            .findAllSimpleVolunteerEventInfosByShelterIdAndDateRange(
                shelterId = shelterId,
                from = from,
                to = to
            )
    }

    @Transactional(readOnly = true)
    override fun getMemberVolunteerEventsByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto> {
        return volunteerEventQueryHandler.findAllSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
            shelterId = shelterId,
            volunteerId = volunteerId,
            from = from,
            to = to
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventDto> {
        return volunteerEventQueryHandler.findAllSimpleVolunteerEventInfosByShelterIdAndDateRange(
            shelterId = shelterId,
            from = from,
            to = to
        )
    }
}
