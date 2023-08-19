package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventHomeRequestDto
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase

@Service
class VolunteerEventHomeApplicationService(
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
    private val getShelterUseCase: GetShelterUseCase,
) {
    @Transactional(readOnly = true)
    fun getVolunteerEventHome(
        reqDto: GetVolunteerEventHomeRequestDto
    ): List<VolunteerSimpleVolunteerEventDto> {
        val shelters = if (reqDto.address != null) {
            getShelterUseCase.getShelterByAddressAndIsFavorite(reqDto.address, reqDto.volunteerId, reqDto.isFavorite)
        } else {
            getShelterUseCase.getShelterByLocationAndIsFavorite(reqDto.latitude!!, reqDto.longitude!!, 50000, reqDto.volunteerId, reqDto.isFavorite)
        }
        val dtos = mutableListOf<VolunteerSimpleVolunteerEventDto>()
        shelters.map {
            dtos.addAll(getVolunteerEventUseCase.getVolunteerEventsByDateRangeAndCategory(it.id, reqDto.from, reqDto.to, reqDto.category))
        }
        return dtos
    }
}
