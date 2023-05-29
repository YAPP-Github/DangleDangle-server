package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.repository.VolunteerActivityTypeJpaRepository

@Component
class VolunteerActivityTypeRepository(
    private val jpaRepository: VolunteerActivityTypeJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
