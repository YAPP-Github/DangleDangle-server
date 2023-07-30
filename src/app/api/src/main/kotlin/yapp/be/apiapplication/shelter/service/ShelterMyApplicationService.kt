package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.shelter.service.model.ShelterVolunteerEventHistoryStatInfo
import yapp.be.domain.model.dto.ShelterSimpleVolunteerEventDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult

@Service
class ShelterMyApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase
) {

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun getShelterVolunteerEventHistories(
        page: Int,
        shelterId: Long,
        status: VolunteerEventStatus?
    ): PagedResult<ShelterSimpleVolunteerEventDto> {
        return getVolunteerEventUseCase
            .getAllShelterVolunteerEvent(
                page = page,
                status = status,
                shelterId = shelterId
            )
    }
}
