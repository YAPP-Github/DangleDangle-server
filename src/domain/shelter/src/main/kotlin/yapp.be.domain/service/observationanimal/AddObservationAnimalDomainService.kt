package yapp.be.domain.service.observationanimal

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.observationanimal.AddObservationAnimalUseCase
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterQueryHandler
import yapp.be.model.enums.observaitonanimal.Gender

@Service
class AddObservationAnimalDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler
) : AddObservationAnimalUseCase {

    @Transactional
    override fun addObservationAnimal(
        shelterId: Long,
        images: List<String>,
        name: String,
        age: String?,
        gender: Gender?,
        breed: String?,
        specialNote: String
    ): ObservationAnimal {

        val shelter = shelterQueryHandler.findById(shelterId)
        val observationAnimal = ObservationAnimal(
            name = name,
            age = age,
            gender = gender,
            profileImageUrl = images.lastOrNull(),
            specialNote = specialNote,
            shelterId = shelter.id,
            breed = breed
        )

        return observationAnimalCommandHandler.create(observationAnimal)
    }
}
