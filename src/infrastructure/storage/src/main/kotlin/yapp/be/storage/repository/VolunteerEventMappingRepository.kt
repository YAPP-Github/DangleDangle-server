package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.repository.VolunteerEventMappingJpaRepository

@Component
class VolunteerEventMappingRepository(
    private val jpaRepository: VolunteerEventMappingJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
