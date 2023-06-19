package yapp.be.domain.port.inbound

import yapp.be.domain.model.ObservationAnimal

interface GetObservationAnimalUseCase {
    fun getObservationAnimalById(observationAnimalId: Long): ObservationAnimal
    fun getAllObservationAnimalsByShelterId(shelterId: Long): List<ObservationAnimal>
}
