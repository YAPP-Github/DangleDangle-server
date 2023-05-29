package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.ObservationAnimalTagMappingQueryHandler
import yapp.be.storage.jpa.repository.ObservationAnimalTagMappingJpaRepository

@Component
class ObservationAnimalTagMappingRepository(
    private val jpaRepository: ObservationAnimalTagMappingJpaRepository
) : ObservationAnimalTagMappingQueryHandler {
    override fun countAll(): Int {
        return jpaRepository.count().toInt()
    }
}
