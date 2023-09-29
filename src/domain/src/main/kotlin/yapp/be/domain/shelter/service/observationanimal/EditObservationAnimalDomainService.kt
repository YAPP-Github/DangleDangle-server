package yapp.be.domain.shelter.service.observationanimal

import org.springframework.stereotype.Service
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.observationanimal.EditObservationAnimalUseCase
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalQueryHandler
import yapp.be.model.enums.observaitonanimal.Gender

@Service
class EditObservationAnimalDomainService(
    private val observationAnimalQueryHandler: ObservationAnimalQueryHandler,
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler
) : EditObservationAnimalUseCase {
    override fun editObservationAnimal(observationAnimalId: Long, shelterId: Long, images: List<String>, name: String, age: String?, gender: Gender?, breed: String?, specialNote: String): ObservationAnimal {
        val observationAnimal = observationAnimalQueryHandler.findById(observationAnimalId)
        val updatedObservationAnimal = ObservationAnimal(
            id = observationAnimal.id,
            profileImageUrl = images.lastOrNull(),
            name = name,
            age = age,
            gender = gender,
            breed = breed,
            specialNote = specialNote,
            shelterId = observationAnimal.shelterId
        )
        return observationAnimalCommandHandler.update(updatedObservationAnimal)
    }
}
