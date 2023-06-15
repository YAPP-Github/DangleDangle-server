package yapp.be.storage.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.port.outbound.ShelterOutLinkCommandHandler
import yapp.be.domain.port.outbound.ShelterOutLinkQueryHandler
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterOutLinkJpaRepository

@Repository
class ShelterOutLinkRepository(
    private val shelterOutLinkJpaRepository: ShelterOutLinkJpaRepository
) : ShelterOutLinkQueryHandler, ShelterOutLinkCommandHandler {
    @Transactional(readOnly = true)
    override fun findAllByShelterId(shelterId: Long): List<ShelterOutLink> {
        val shelterOutLinkEntities = shelterOutLinkJpaRepository.findAllByShelterId(shelterId)
        return shelterOutLinkEntities.map { it.toDomainModel() }
    }

    override fun upsertAll(outLinks: List<ShelterOutLink>): List<ShelterOutLink> {
        val shelterOutLinkEntities = outLinks.map { it.toEntityModel() }
        shelterOutLinkJpaRepository.saveAll(shelterOutLinkEntities)

        return shelterOutLinkEntities.map { it.toDomainModel() }
    }
}
