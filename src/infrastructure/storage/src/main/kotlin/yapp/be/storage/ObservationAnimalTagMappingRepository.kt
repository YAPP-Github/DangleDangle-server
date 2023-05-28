package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.ObservationAnimalTagMappingJpaRepository

@Component
class ObservationAnimalTagMappingRepository(
    private val jpaRepository: ObservationAnimalTagMappingJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
