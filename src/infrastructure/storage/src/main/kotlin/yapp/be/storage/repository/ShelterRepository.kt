package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.storage.jpa.repository.ShelterJpaRepository
import yapp.be.storage.jpa.repository.ShelterOutlinkJpaRepository

@Component
class ShelterRepository(
    private val shelterJpaRepository: ShelterJpaRepository,
    private val shelterOutlinkJpaRepository: ShelterOutlinkJpaRepository,
) : ShelterQueryHandler {
    override fun countAll(): Int {
        return shelterJpaRepository.count().toInt()
    }
}
