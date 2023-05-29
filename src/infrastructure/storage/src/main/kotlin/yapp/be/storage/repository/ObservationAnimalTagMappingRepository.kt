package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.repository.ObservationAnimalTagMappingJpaRepository

@Component
class ObservationAnimalTagMappingRepository(
    private val jpaRepository: ObservationAnimalTagMappingJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
