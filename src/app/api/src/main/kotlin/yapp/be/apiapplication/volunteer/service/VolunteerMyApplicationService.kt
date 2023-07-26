package yapp.be.apiapplication.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.VolunteerVolunteerEventHistoryStatInfo
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetVolunteerUseCase

@Service
class VolunteerMyApplicationService(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase
) {

    @Transactional(readOnly = true)
    fun getVolunteerMyProfile(volunteerId: Long): GetVolunteerMyProfileResponseDto {
        val volunteer = getVolunteerUseCase.getById(volunteerId = volunteerId)
        val volunteerEventHistoryStat = getVolunteerEventUseCase
            .getVolunteerVolunteerEventStat(volunteerId)

        return GetVolunteerMyProfileResponseDto(
            name = volunteer.nickname,
            historyStat = VolunteerVolunteerEventHistoryStatInfo(
                done = volunteerEventHistoryStat.done,
                waiting = volunteerEventHistoryStat.waiting,
                joining = volunteerEventHistoryStat.joining
            )
        )
    }
}
