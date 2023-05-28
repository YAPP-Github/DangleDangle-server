package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.VolunteerEventActivityTypeMappingJpaRepository

@Component
class VolunteerEventActivityTypeMappingRepository(
    private val jpaRepository: VolunteerEventActivityTypeMappingJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
