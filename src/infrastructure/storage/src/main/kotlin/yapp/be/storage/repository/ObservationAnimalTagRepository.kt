package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.repository.ObservationAnimalTagJpaRepository

@Component
class ObservationAnimalTagRepository(
    private val jpaRepository: ObservationAnimalTagJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
