package yapp.be.apiapplication.shelter.service.observationanimal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.DeleteObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.GetObservationAnimalResponseDto
import yapp.be.domain.port.inbound.CreateObservationAnimalUseCase
import yapp.be.domain.port.inbound.DeleteObservationAnimalUseCase
import yapp.be.domain.port.inbound.EditObservationAnimalUseCase
import yapp.be.domain.port.inbound.GetObservationAnimalUseCase

@Service
class ObservationAnimalManageApplicationService(
    private val getObservationAnimalUseCase: GetObservationAnimalUseCase,
    private val createObservationAnimalUseCase: CreateObservationAnimalUseCase,
    private val editObservationAnimalUseCase: EditObservationAnimalUseCase,
    private val deleteObservationAnimalUseCase: DeleteObservationAnimalUseCase
) {

    @Transactional(readOnly = true)
    fun getObservationAnimal(observationAnimalId: Long): GetObservationAnimalResponseDto {
        val observationAnimal = getObservationAnimalUseCase.getObservationAnimalById(observationAnimalId)

        return GetObservationAnimalResponseDto(
            id = observationAnimal.id,
            shelterId = observationAnimal.shelterId,
            name = observationAnimal.name,
            age = observationAnimal.age,
            gender = observationAnimal.gender,
            profileImageUrl = observationAnimal.profileImageUrl,
            specialNote = observationAnimal.specialNote
        )
    }

    @Transactional
    fun addObservationAnimal(shelterId: Long, reqDto: AddObservationAnimalRequestDto): AddObservationAnimalResponseDto {

        val observationAnimal = createObservationAnimalUseCase.addObservationAnimal(
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

    @Transactional
    fun editObservationAnimal(shelterId: Long, observationAnimalId: Long, reqDto: EditObservationAnimalRequestDto): EditObservationAnimalResponseDto {
        val observationAnimal = editObservationAnimalUseCase.editObservationAnimal(
            observationAnimalId = observationAnimalId,
            shelterId = shelterId,
            name = reqDto.name,
            age = reqDto.age,
            specialNote = reqDto.specialNote,
            gender = reqDto.gender,
            breed = reqDto.breed,
            images = reqDto.images
        )

        return EditObservationAnimalResponseDto(
            observationAnimalId = observationAnimal.id
        )
    }

    @Transactional
    fun deleteObservationAnimal(observationAnimalId: Long): DeleteObservationAnimalResponseDto {
        val observationAnimal = deleteObservationAnimalUseCase.deleteObservationAnimal(observationAnimalId = observationAnimalId)
        return DeleteObservationAnimalResponseDto(observationAnimal.id)
    }
}
