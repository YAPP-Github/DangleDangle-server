package yapp.be.storage.repository.command

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterUser
import yapp.be.domain.port.outbound.shelteruser.ShelterUserCommandHandler
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterUserJpaRepository

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
}
