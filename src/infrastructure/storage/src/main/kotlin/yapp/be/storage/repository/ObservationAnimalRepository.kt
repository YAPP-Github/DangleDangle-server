package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ObservationAnimalQueryHandler
import yapp.be.storage.jpa.repository.ObservationAnimalJpaRepository

@Component
class ObservationAnimalRepository(
    private val jpaRepository: ObservationAnimalJpaRepository
) : ObservationAnimalQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
