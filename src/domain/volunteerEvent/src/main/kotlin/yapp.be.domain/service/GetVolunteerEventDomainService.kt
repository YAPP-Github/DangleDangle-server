package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import java.time.Month
import java.time.Year

@Service
class GetVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler
) : GetVolunteerEventUseCase {
    @Transactional(readOnly = true)
    override fun getMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        year: Year,
        month: Month
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndYearAndMonth(
            shelterId = shelterId,
            volunteerId = volunteerId,
            year = year.value,
            month = month.value
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        year: Year,
        month: Month
    ): List<SimpleVolunteerEventInfo> {
        return volunteerEventQueryHandler.findAllByShelterIdAndYearAndMonth(
            shelterId = shelterId,
            year = year.value,
            month = month.value
        )
    }
}
