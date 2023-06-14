package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.model.Shelter
import yapp.be.domain.port.outbound.ShelterCommandHandler
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.storage.jpa.shelter.repository.ShelterJpaRepository
import yapp.be.storage.jpa.shelter.repository.ShelterOutLinkJpaRepository

@Component
class ShelterRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterOutLinkJpaRepository: ShelterOutLinkJpaRepository,
) : ShelterQueryHandler, ShelterCommandHandler {
    override fun countAll(): Int {
        return shelterJpaRepository.count().toInt()
    }

    override fun create(shelter: Shelter): Shelter {
    }
}
