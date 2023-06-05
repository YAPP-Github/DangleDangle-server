package yapp.be.storage.jpa.observationanimal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalTagMappingEntity

@Repository
interface ObservationAnimalTagMappingJpaRepository : JpaRepository<ObservationAnimalTagMappingEntity, Long>
