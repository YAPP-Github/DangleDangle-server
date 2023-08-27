package yapp.be.storage.repository.command

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterUserJpaRepository
import yapp.be.storage.jpa.volunteer.model.mappers.toDomainModel

@Component
@Transactional
class ShelterUserCommandRepository(
    private val shelterUserJpaRepository: ShelterUserJpaRepository
) : ShelterUserCommandHandler {

    override fun save(shelterUser: ShelterUser): ShelterUser {
        val shelterUserEntity =
            shelterUserJpaRepository.save(
                shelterUser.toEntityModel()
            )
        return shelterUserEntity.toDomainModel()
    }

    override fun delete(shelterUserId: Long): Long {
        val shelterUserEntity = shelterUserJpaRepository.findByIdOrNull(shelterUserId) ?: throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        shelterUserJpaRepository.delete(shelterUserEntity)
        return shelterUserEntity.shelterId
    }
}
