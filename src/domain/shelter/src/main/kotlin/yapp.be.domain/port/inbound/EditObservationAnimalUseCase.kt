package yapp.be.domain.port.inbound

import yapp.be.domain.model.ObservationAnimal
import yapp.be.enum.Gender

interface EditObservationAnimalUseCase {

    fun editObservationAnimal(
        observationAnimalId: Long,
        shelterId: Long,
        images: List<String>,
        name: String,
        age: Int,
        gender: Gender,
        breed: String,
        specialNote: String
    ): ObservationAnimal
}
