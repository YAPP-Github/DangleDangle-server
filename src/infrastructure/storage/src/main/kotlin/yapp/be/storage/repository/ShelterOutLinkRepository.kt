package yapp.be.storage.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.port.outbound.ShelterOutLinkCommandHandler
import yapp.be.domain.port.outbound.ShelterOutLinkQueryHandler
import yapp.be.storage.jpa.shelter.model.mappers.toDomainModel
import yapp.be.storage.jpa.shelter.model.mappers.toEntityModel
import yapp.be.storage.jpa.shelter.repository.ShelterOutLinkJpaRepository

@Component
class ShelterOutLinkRepository(
    private val shelterOutLinkJpaRepository: ShelterOutLinkJpaRepository
) : ShelterOutLinkQueryHandler, ShelterOutLinkCommandHandler {
    @Transactional(readOnly = true)
    override fun findAllByShelterId(shelterId: Long): List<ShelterOutLink> {
        val shelterOutLinkEntities = shelterOutLinkJpaRepository.findAllByShelterId(shelterId)
        return shelterOutLinkEntities.map { it.toDomainModel() }
    }

    override fun upsertAll(shelterId: Long, outLinks: List<ShelterOutLink>): List<ShelterOutLink> {
        if (outLinks.isEmpty()) {
            shelterOutLinkJpaRepository.deleteAllByShelterId(shelterId)
        } else {
            val upsertOutLinkEntitiesMap = outLinks
                .map { it.toEntityModel() }
                .associateBy { it.outLinkType }

            val dbOutLinksEntitiesMap = shelterOutLinkJpaRepository.findAllByShelterId(shelterId)
                .associateBy { it.outLinkType }

            /**
             * DB에 있는데 UPDATE 대상으로 잡히지 않았다면 DELETE 한다.
             */
            dbOutLinksEntitiesMap
                .keys
                .subtract(upsertOutLinkEntitiesMap.keys)
                .forEach { outLinkType ->
                    val outLinkEntityToDelete = dbOutLinksEntitiesMap[outLinkType]!!
                    shelterOutLinkJpaRepository.delete(outLinkEntityToDelete)
                }

            /**
             * DB에 있으면 INSERT, 있다면 UPDATE 한다.
             */
            shelterOutLinkJpaRepository.saveAll(upsertOutLinkEntitiesMap.values)
        }
        return outLinks
    }
}
