package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.AddObservationAnimalUseCase
import yapp.be.domain.port.outbound.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.enum.Gender

@Service
class ManageObservationAnimalDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler
) : AddObservationAnimalUseCase {
    override fun addObservationAnimal(
        shelterId: Long,
        images: List<String>,
        name: String,
        age: Int,
        gender: Gender,
        breed: String,
        specialNote: String
    ): ObservationAnimal {

        val shelter = shelterQueryHandler.findById(shelterId)
        val observationAnimal = ObservationAnimal(
            name = name,
            age = age,
            gender = gender,
            profileImageUrl = images.last(),
            specialNote = specialNote,
            shelterId = shelter.id
        )

        return observationAnimalCommandHandler.create(observationAnimal)
    }
}
