package yapp.be.storage

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerQueryHandler
import yapp.be.storage.jpa.ObservationAnimalTagJpaRepository

@Component
class ObservationAnimalTagRepository(
    private val jpaRepository: ObservationAnimalTagJpaRepository
) : VolunteerQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
