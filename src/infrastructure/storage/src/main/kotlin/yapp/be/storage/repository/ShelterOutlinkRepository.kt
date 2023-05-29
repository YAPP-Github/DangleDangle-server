package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.storage.jpa.repository.ShelterOutlinkJpaRepository

@Component
class ShelterOutlinkRepository(
    private val jpaRepository: ShelterOutlinkJpaRepository
) : ShelterQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
