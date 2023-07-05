package yapp.be.apiapplication.shelter.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.shelter.model.BookMarkShelterRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.BookMarkShelterResponseDto
import yapp.be.domain.port.inbound.BookMarkShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUseCase

@Service
class ShelterApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val bookMarkShelterUseCase: BookMarkShelterUseCase,
) {
    @Transactional
    fun bookMarkShelter(reqDto: BookMarkShelterRequestDto): BookMarkShelterResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId = reqDto.shelterId)
        val shelterBookMark = bookMarkShelterUseCase.doBookMark(
            shelterId = shelter.id,
            volunteerId = reqDto.volunteerId
        )

        return BookMarkShelterResponseDto(
            shelterId = shelter.id,
            volunteerId = reqDto.volunteerId,
            bookMarked = shelterBookMark != null
        )
    }
}
