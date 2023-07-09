package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetObservationAnimalResponseDto
import yapp.be.domain.port.inbound.GetObservationAnimalUseCase
import yapp.be.model.support.PagedResult

@Service
class ObservationAnimalApplicationService(
    private val getObservationAnimalUseCase: GetObservationAnimalUseCase
) {

    @Transactional(readOnly = true)
    fun getShelterObservationAnimals(
        page: Int,
        shelterId: Long
    ): PagedResult<GetObservationAnimalResponseDto> {

        val observationAnimals =
            getObservationAnimalUseCase.getAllObservationAnimalsByShelterId(
                shelterId = shelterId,
                page = page
            )

        return PagedResult(
            pageNumber = observationAnimals.pageNumber,
            pageSize = observationAnimals.pageSize,
            content = observationAnimals.content.map { observationAnimal ->
                GetObservationAnimalResponseDto(
                    id = observationAnimal.id,
                    shelterId = observationAnimal.shelterId,
                    name = observationAnimal.name,
                    age = observationAnimal.age,
                    gender = observationAnimal.gender,
                    profileImageUrl = observationAnimal.profileImageUrl,
                    specialNote = observationAnimal.specialNote,
                    breed = observationAnimal.breed
                )
            }.toList()
        )
    }
}
