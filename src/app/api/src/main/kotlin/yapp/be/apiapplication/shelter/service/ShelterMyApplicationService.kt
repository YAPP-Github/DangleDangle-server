package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.shelter.service.model.ShelterVolunteerEventHistoryStatInfo
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase

@Service
class ShelterMyApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase
) {

    fun getShelterMyProfile(shelterId: Long): GetShelterMyProfileResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val volunteerEventHistoryStat = getVolunteerEventUseCase.getShelterVolunteerEventStat(shelterId)

        return GetShelterMyProfileResponseDto(
            name = shelter.name,
            historyStat = ShelterVolunteerEventHistoryStatInfo(
                done = volunteerEventHistoryStat.done,
                inProgress = volunteerEventHistoryStat.inProgress
            )
        )
    }
}
