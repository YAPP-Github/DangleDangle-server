package yapp.be.domain.port.inbound

import yapp.be.domain.model.ObservationAnimal

interface DeleteObservationAnimalUseCase {
    fun deleteObservationAnimal(observationAnimalId: Long): ObservationAnimal
}
