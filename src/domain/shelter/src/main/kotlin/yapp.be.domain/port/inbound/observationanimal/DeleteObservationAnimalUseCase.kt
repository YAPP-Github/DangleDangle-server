package yapp.be.domain.port.inbound.observationanimal

import yapp.be.domain.model.ObservationAnimal

interface DeleteObservationAnimalUseCase {
    fun deleteObservationAnimal(observationAnimalId: Long, shelterId: Long): ObservationAnimal
}
