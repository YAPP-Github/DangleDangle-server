package yapp.be.domain.port.inbound

import yapp.be.domain.model.ObservationAnimal
import yapp.be.model.support.PagedResult

interface GetObservationAnimalUseCase {
    fun getObservationAnimalById(observationAnimalId: Long): ObservationAnimal
    fun getAllObservationAnimalsByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ObservationAnimal>

    fun getObservationAnimalByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal
}
