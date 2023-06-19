package yapp.be.domain.port.outbound

import yapp.be.domain.model.ObservationAnimal

interface ObservationAnimalCommandHandler {
    fun create(observationAnimal: ObservationAnimal): ObservationAnimal
    fun update(observationAnimal: ObservationAnimal): ObservationAnimal

    fun delete(observationAnimalId: Long): ObservationAnimal
}
