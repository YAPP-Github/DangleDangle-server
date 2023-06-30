package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.inbound.CreateObservationAnimalUseCase
import yapp.be.domain.port.inbound.DeleteObservationAnimalUseCase
import yapp.be.domain.port.inbound.EditObservationAnimalUseCase
import yapp.be.domain.port.inbound.GetObservationAnimalUseCase
import yapp.be.domain.port.outbound.ObservationAnimalCommandHandler
import yapp.be.domain.port.outbound.ObservationAnimalQueryHandler
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.enum.Gender

@Service
class ManageObservationAnimalDomainService(
    private val shelterQueryHandler: ShelterQueryHandler,
    private val observationAnimalQueryHandler: ObservationAnimalQueryHandler,
    private val observationAnimalCommandHandler: ObservationAnimalCommandHandler
) : CreateObservationAnimalUseCase, GetObservationAnimalUseCase, EditObservationAnimalUseCase, DeleteObservationAnimalUseCase {

    @Transactional(readOnly = true)
    override fun getAllObservationAnimalsByShelterId(shelterId: Long): List<ObservationAnimal> {
        return observationAnimalQueryHandler.findAllByShelterId(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getObservationAnimalByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal {
        return observationAnimalQueryHandler.findByIdAndShelterId(
            observationAnimalId = observationAnimalId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getObservationAnimalById(observationAnimalId: Long): ObservationAnimal {
        return observationAnimalQueryHandler.findById(observationAnimalId)
    }

    @Transactional
    override fun addObservationAnimal(
        shelterId: Long,
        images: List<String>,
        name: String,
        age: String,
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
            shelterId = shelter.id,
            breed = breed
        )

        return observationAnimalCommandHandler.create(observationAnimal)
    }

    @Transactional
    override fun editObservationAnimal(
        observationAnimalId: Long,
        shelterId: Long,
        images: List<String>,
        name: String,
        age: String,
        gender: Gender,
        breed: String,
        specialNote: String
    ): ObservationAnimal {
        val observationAnimal = observationAnimalQueryHandler.findById(observationAnimalId)
        val updatedObservationAnimal = ObservationAnimal(
            id = observationAnimal.id,
            profileImageUrl = images.last(),
            name = name,
            age = age,
            gender = gender,
            breed = breed,
            specialNote = specialNote,
            shelterId = observationAnimal.shelterId
        )
        return observationAnimalCommandHandler.update(updatedObservationAnimal)
    }

    @Transactional
    override fun deleteObservationAnimal(observationAnimalId: Long): ObservationAnimal {
        return observationAnimalCommandHandler.delete(observationAnimalId)
    }
}
