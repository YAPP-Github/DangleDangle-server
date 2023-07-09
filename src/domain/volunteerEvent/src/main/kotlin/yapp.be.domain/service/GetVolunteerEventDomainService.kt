package yapp.be.domain.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler

@Service
class GetVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler
) : GetVolunteerEventUseCase {

    @Transactional(readOnly = true)
    override fun getVolunteerEvent(shelterId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler.findByIdAndShelterId(
            id = volunteerEventId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndYearAndMonth(
            shelterId = shelterId,
            volunteerId = volunteerId,
            from = from,
            to = to
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllByShelterIdAndYearAndMonth(
            shelterId = shelterId,
            from = from,
            to = to
        )
    }
}
