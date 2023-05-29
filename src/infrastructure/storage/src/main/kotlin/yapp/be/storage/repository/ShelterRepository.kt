package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ShelterQueryHandler
import yapp.be.storage.jpa.repository.ShelterJpaRepository

@Component
class ShelterRepository(
    private val jpaRepository: ShelterJpaRepository
) : ShelterQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
