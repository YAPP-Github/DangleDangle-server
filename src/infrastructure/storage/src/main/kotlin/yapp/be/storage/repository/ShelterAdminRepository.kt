package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ShelterAdminQueryHandler
import yapp.be.storage.jpa.repository.ShelterAdminJpaRepository

@Component
class ShelterAdminRepository(
    private val jpaRepository: ShelterAdminJpaRepository
) : ShelterAdminQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
