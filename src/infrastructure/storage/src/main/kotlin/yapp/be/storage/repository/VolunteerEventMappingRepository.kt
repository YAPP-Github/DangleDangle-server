package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerEventMappingQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventMappingJpaRepository

@Component
class VolunteerEventMappingRepository(
    private val jpaRepository: VolunteerEventMappingJpaRepository
) : VolunteerEventMappingQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
