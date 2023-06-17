package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import yapp.be.apiapplication.shelter.service.model.AddObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.model.AddObservationAnimalResponseDto
import yapp.be.domain.port.inbound.AddObservationAnimalUseCase

@Service
class ObservationAnimalManageApplicationService(
    private val addObservationAnimalUseCase: AddObservationAnimalUseCase
) {
    fun addObservationAnimal(shelterId: Long, reqDto: AddObservationAnimalRequestDto): AddObservationAnimalResponseDto {

        val observationAnimal = addObservationAnimalUseCase.addObservationAnimal(
            shelterId = shelterId,
            name = reqDto.name,
            age = reqDto.age,
            specialNote = reqDto.specialNote,
            gender = reqDto.gender,
            breed = reqDto.breed,
            images = reqDto.images
        )
        return AddObservationAnimalResponseDto(
            observationAnimalId = observationAnimal.id
        )
    }
}
