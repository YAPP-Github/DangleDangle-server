package yapp.be.storage.repository.query

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.port.outbound.shelter.ShelterOutLinkQueryHandler
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.repository.ShelterOutLinkJpaRepository

@Component
@Transactional(readOnly = true)
class ShelterOutLinkQueryRepository(
    private val shelterOutLinkJpaRepository: ShelterOutLinkJpaRepository
) : ShelterOutLinkQueryHandler {
    override fun findAllByShelterId(shelterId: Long): List<ShelterOutLink> {
        val shelterOutLinkEntities = shelterOutLinkJpaRepository.findAllByShelterId(shelterId)
        return shelterOutLinkEntities.map { it.toDomainModel() }
    }
}
