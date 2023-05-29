package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerEventActivityTypeQueryHandler
import yapp.be.storage.jpa.repository.VolunteerActivityTypeJpaRepository

@Component
class VolunteerEventActivityTypeRepository(
    private val jpaRepository: VolunteerActivityTypeJpaRepository
) : VolunteerEventActivityTypeQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
