package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerEventActivityTypeMappingQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventActivityTypeMappingJpaRepository

@Component
class VolunteerEventActivityTypeMappingRepository(
    private val jpaRepository: VolunteerEventActivityTypeMappingJpaRepository
) : VolunteerEventActivityTypeMappingQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
