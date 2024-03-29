package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventHomeRequestDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.domain.shelter.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.volunteerActivity.model.dto.VolunteerSimpleVolunteerActivityDto
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase
import yapp.be.exceptions.CustomException

@Service
class VolunteerActivityHomeApplicationService(
    private val getVolunteerEventUseCase: GetVolunteerActivityUseCase,
    private val getShelterUseCase: GetShelterUseCase,
) {
    @Transactional(readOnly = true)
    fun getVolunteerEventHome(
        reqDto: GetVolunteerEventHomeRequestDto
    ): List<VolunteerSimpleVolunteerActivityDto> {
        val shelters = if (reqDto.address != null && reqDto.volunteerId != null) {
            getShelterUseCase.getShelterByAddressAndIsFavorite(reqDto.address, reqDto.volunteerId, reqDto.isFavorite)
        } else {
            if (reqDto.latitude == null || reqDto.longitude == null)
                throw CustomException(ApiExceptionType.INVALID_PARAMETER, "위도 / 경도 값은 null일 수 없습니다.")
            getShelterUseCase.getShelterByLocationAndIsFavorite(reqDto.latitude, reqDto.longitude, 50000, reqDto.volunteerId, reqDto.isFavorite)
        }

        return buildList {
            shelters.forEach {
                addAll(getVolunteerEventUseCase.getVolunteerActivitiesByDateRangeAndCategory(it.id, reqDto.from, reqDto.to, reqDto.category))
            }
        }
    }
}
