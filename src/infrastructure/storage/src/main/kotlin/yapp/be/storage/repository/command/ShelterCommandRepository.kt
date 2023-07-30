package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Shelter
import yapp.be.domain.model.ShelterBookMark
import yapp.be.domain.port.outbound.shelter.ShelterBookMarkCommandHandler
import yapp.be.domain.port.outbound.shelter.ShelterCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterBookMarkJpaRepository
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository

@Component
@Transactional
class ShelterCommandRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterBookMarkJpaRepository: ShelterBookMarkJpaRepository
) : ShelterCommandHandler, ShelterBookMarkCommandHandler {

    override fun create(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.save(
            shelter.toEntityModel()
        )

        return shelterEntity.toDomainModel()
    }

    override fun update(shelter: Shelter): Shelter {
        val shelterEntity = shelterJpaRepository.findByIdOrNull(shelter.id)
            ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "Shelter Not Found")
        shelterEntity.update(shelter)
        shelterJpaRepository.save(shelterEntity)

        return shelterEntity.toDomainModel()
    }

    override fun saveBookMark(shelterBookMark: ShelterBookMark): ShelterBookMark {
        val shelterBookMarkEntity = shelterBookMarkJpaRepository.save(
            shelterBookMark.toEntityModel()
        )
        return shelterBookMarkEntity.toDomainModel()
    }

    override fun deleteBookMark(
        shelterBookMark: ShelterBookMark
    ) {
        val shelterBookMarkEntity = shelterBookMark.toEntityModel()
        shelterBookMarkJpaRepository.delete(shelterBookMarkEntity)
    }
}
