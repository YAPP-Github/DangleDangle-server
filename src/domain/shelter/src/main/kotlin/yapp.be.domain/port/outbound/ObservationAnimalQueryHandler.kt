package yapp.be.domain.port.outbound

import yapp.be.domain.model.ObservationAnimal
import yapp.be.model.support.PagedResult

interface ObservationAnimalQueryHandler {
    fun findById(observationAnimalId: Long): ObservationAnimal
    fun findAllByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ObservationAnimal>

    fun findByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal
}
