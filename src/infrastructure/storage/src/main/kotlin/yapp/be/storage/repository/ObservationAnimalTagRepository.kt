package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ObservationAnimalTagQueryHandler
import yapp.be.storage.jpa.repository.ObservationAnimalTagJpaRepository

@Component
class ObservationAnimalTagRepository(
    private val jpaRepository: ObservationAnimalTagJpaRepository
) : ObservationAnimalTagQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
