package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ObservationAnimal
import yapp.be.domain.port.outbound.observationanimal.ObservationAnimalCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.observationanimal.model.mappers.toDomainModel
import yapp.be.storage.jpa.observationanimal.model.mappers.toEntityModel
import yapp.be.storage.jpa.observationanimal.repository.ObservationAnimalJpaRepository

@Component
@Transactional
class ObservationAnimalCommandRepository(
    private val observationAnimalJpaRepository: ObservationAnimalJpaRepository,
) : ObservationAnimalCommandHandler {

    override fun create(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimal.toEntityModel()
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    override fun update(observationAnimal: ObservationAnimal): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdOrNull(observationAnimal.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        observationAnimalEntity.update(observationAnimal)
        return observationAnimalJpaRepository.save(observationAnimalEntity).toDomainModel()
    }

    override fun delete(
        observationAnimalId: Long,
        shelterId: Long
    ): ObservationAnimal {
        val observationAnimalEntity = observationAnimalJpaRepository.findByIdAndShelterId(
            id = observationAnimalId,
            shelterId = shelterId
        )
            ?: throw CustomException(
                StorageExceptionType.ENTITY_NOT_FOUND, "Observation Animal Not Found"
            )
        observationAnimalJpaRepository.delete(observationAnimalEntity)

        return observationAnimalEntity.toDomainModel()
    }

    override fun deleteAllByShelterId(shelterId: Long) {
        val observationAnimals = observationAnimalJpaRepository.findAllByShelterId(shelterId)
        observationAnimals.forEach { observationAnimalJpaRepository.delete(it) }
    }
}
