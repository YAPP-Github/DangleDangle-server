package yapp.be.domain.port.outbound

import yapp.be.domain.model.ObservationAnimal

interface ObservationAnimalQueryHandler {
    fun findById(observationAnimalId: Long): ObservationAnimal
    fun findAllByShelterId(shelterId: Long): List<ObservationAnimal>

    fun findByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal
}
