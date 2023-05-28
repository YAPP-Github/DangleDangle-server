package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.ShelterJpaRepository

@Component
class ShelterRepository(
    private val jpaRepository: ShelterJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
