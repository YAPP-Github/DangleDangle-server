package yapp.be.storage.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.ShelterUserCommandHandler
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository
import yapp.be.storage.jpa.shelter.repository.querydsl.ShelterUserJpaRepository

@Component
class ShelterUserRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterUserJpaRepository: ShelterUserJpaRepository
) : ShelterUserCommandHandler {
    @Transactional
    override fun save(shelterUser: ShelterUser): ShelterUser {
        val shelterUserEntity =
            shelterUserJpaRepository.save(
                shelterUser.toEntityModel()
            )
        return shelterUserEntity.toDomainModel()
    }
}
