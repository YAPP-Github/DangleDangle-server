package yapp.be.apiapplication.shelter.service.observationanimal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.AddObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.DeleteObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalRequestDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.EditObservationAnimalResponseDto
import yapp.be.apiapplication.shelter.service.observationanimal.model.GetObservationAnimalResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.domain.port.inbound.CreateObservationAnimalUseCase
import yapp.be.domain.port.inbound.DeleteObservationAnimalUseCase
import yapp.be.domain.port.inbound.EditObservationAnimalUseCase
import yapp.be.domain.port.inbound.GetObservationAnimalUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.exceptions.CustomException

@Service
class ObservationAnimalManageApplicationService(
    private val getShelterUserUseCase: GetShelterUserUseCase,
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
    fun addObservationAnimal(shelterUserId: Long, reqDto: AddObservationAnimalRequestDto): AddObservationAnimalResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)
        val observationAnimal = createObservationAnimalUseCase.addObservationAnimal(
            shelterId = shelterUser.id,
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
    fun editObservationAnimal(shelterId: Long, shelterUserId: Long, observationAnimalId: Long, reqDto: EditObservationAnimalRequestDto): EditObservationAnimalResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)
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

        if (shelterUser.shelterId != observationAnimal.shelterId) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

        return EditObservationAnimalResponseDto(
            observationAnimalId = observationAnimal.id
        )
    }

    @Transactional
    fun deleteObservationAnimal(
        observationAnimalId: Long,
        shelterUserId: Long
    ): DeleteObservationAnimalResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)
        val observationAnimal = deleteObservationAnimalUseCase.deleteObservationAnimal(observationAnimalId = observationAnimalId)

        if (shelterUser.shelterId != observationAnimal.shelterId) {
            throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "접근 권한이 없습니다.")
        }

        return DeleteObservationAnimalResponseDto(observationAnimal.id)
    }
}
