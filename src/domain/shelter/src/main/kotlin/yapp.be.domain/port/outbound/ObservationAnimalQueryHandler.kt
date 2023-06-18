package yapp.be.domain.port.outbound

import yapp.be.domain.model.ObservationAnimal

interface ObservationAnimalQueryHandler {
    fun findById(observationAnimalId: Long): ObservationAnimal
}
