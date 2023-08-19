package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetShelterHomeRequestDto
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase

@Service
class ShelterHomeApplicationService(
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
) {
    @Transactional(readOnly = true)
    fun getShelterEventHome(
        reqDto: GetShelterHomeRequestDto
    ): List<VolunteerSimpleVolunteerEventDto> {
        val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)
        return getVolunteerEventUseCase.getVolunteerEventsByDateRangeAndCategoryAndStatus(shelterUser.shelterId, reqDto.from, reqDto.to, reqDto.category, reqDto.status)
    }
}
