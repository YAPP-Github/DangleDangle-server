package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetShelterHomeRequestDto
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase
import yapp.be.domain.volunteerActivity.model.dto.VolunteerSimpleVolunteerActivityDto
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase

@Service
class ShelterHomeApplicationService(
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase,
) {
    @Transactional(readOnly = true)
    fun getShelterEventHome(
        reqDto: GetShelterHomeRequestDto
    ): List<VolunteerSimpleVolunteerActivityDto> {
        val shelterUser = getShelterUserUseCase.getShelterUserById(reqDto.shelterUserId)
        return getVolunteerActivityUseCase.getVolunteerActivitiesByDateRangeAndCategoryAndStatus(shelterUser.shelterId, reqDto.from, reqDto.to, reqDto.category, reqDto.status)
    }
}
