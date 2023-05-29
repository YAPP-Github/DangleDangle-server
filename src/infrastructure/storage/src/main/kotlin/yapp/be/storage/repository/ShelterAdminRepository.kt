package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.repository.ShelterAdminJpaRepository

@Component
class ShelterAdminRepository(
    private val jpaRepository: ShelterAdminJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
