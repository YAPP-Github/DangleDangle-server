package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler

@Service
class GetVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler
) : GetVolunteerEventUseCase {
    @Transactional(readOnly = true)
    override fun getMemberVolunteerEvents(
        shelterId: Long,
        volunteerId: Long
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllWithVolunteerParticipationStatusByShelterIdAndVolunteerId(
            shelterId = shelterId,
            volunteerId = volunteerId
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEvents(
        shelterId: Long
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllByShelterId(
            shelterId = shelterId
        )
    }
}
