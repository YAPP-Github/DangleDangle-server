package yapp.be.storage.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterBookMark
import yapp.be.domain.port.outbound.ShelterBookMarkCommandHandler
import yapp.be.domain.port.outbound.ShelterBookMarkQueryHandler
import yapp.be.domain.port.outbound.ShelterCommandHandler
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterBookMarkJpaRepository
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository

@Component
class ShelterRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterBookMarkJpaRepository: ShelterBookMarkJpaRepository
) : ShelterQueryHandler, ShelterCommandHandler, ShelterBookMarkQueryHandler, ShelterBookMarkCommandHandler {

    @Transactional(readOnly = true)
    override fun findById(id: Long): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(id) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        return shelterEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun existByName(name: String): Boolean {
        return shelterJpaRepository.findByName(name) != null
    }

    @Transactional
    override fun create(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.save(
            shelter.toEntityModel()
        )

        return shelterEntity.toDomainModel()
    }

    @Transactional
    override fun update(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(shelter.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        shelterEntity.update(shelter)
        shelterJpaRepository.save(shelterEntity)

        return shelterEntity.toDomainModel()
    }

    @Transactional(readOnly = true)
    override fun getShelterIdAndVolunteerId(shelterId: Long, volunteerId: Long): ShelterBookMark? {
        val shelterBookMarkEntity = shelterBookMarkJpaRepository.findByShelterIdAndVolunteerId(
            shelterId = shelterId,
            volunteerId = volunteerId
        )

        return shelterBookMarkEntity?.toDomainModel()
    }

    @Transactional
    override fun saveBookMark(shelterBookMark: ShelterBookMark): ShelterBookMark {
        val shelterBookMarkEntity = shelterBookMarkJpaRepository.save(
            shelterBookMark.toEntityModel()
        )
        return shelterBookMarkEntity.toDomainModel()
    }

    @Transactional
    override fun deleteBookMark(
        shelterBookMark: ShelterBookMark
    ) {
        val shelterBookMarkEntity = shelterBookMark.toEntityModel()
        shelterBookMarkJpaRepository.delete(shelterBookMarkEntity)
    }
}
