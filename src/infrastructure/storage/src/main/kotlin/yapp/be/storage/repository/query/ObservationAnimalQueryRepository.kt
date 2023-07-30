package yapp.be.storage.repository.query

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.model.support.PagedResult
import yapp.be.storage.config.PAGE_SIZE
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository

@Component
@Transactional(readOnly = true)
class ObservationAnimalQueryRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
) : ObservationAnimalQueryHandler {

    override fun findAllByShelterId(
        page: Int,
        shelterId: Long
    ): PagedResult<ObservationAnimal> {
        val pageable = PageRequest.of(
            page, PAGE_SIZE
        )
        val observationAnimalEntities = observationAnimalJpaRepository.findAllByShelterId(
            shelterId = shelterId,
            pageable = pageable
        )
        return PagedResult(
            pageSize = observationAnimalEntities.size,
            pageNumber = observationAnimalEntities.number,
            content = observationAnimalEntities.content.map { it.toDomainModel() }
        )
    }

    override fun findByIdAndShelterId(observationAnimalId: Long, shelterId: Long): ObservationAnimal {
        return observationAnimalJpaRepository.findByIdAndShelterId(
            id = observationAnimalId,
            shelterId = shelterId
        )?.toDomainModel() ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found")
    }

    override fun findById(observationAnimalId: Long): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimalId)
            ?: throw CustomException(
                StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
            )
        return observationAnimalEntity.toDomainModel()
    }
}
