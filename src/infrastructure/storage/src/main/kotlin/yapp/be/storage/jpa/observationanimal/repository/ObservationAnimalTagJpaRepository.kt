package yapp.be.storage.jpa.observationanimal.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalTagEntity

@Repository
interface ObservationAnimalTagJpaRepository : JpaRepository<ObservationAnimalTagEntity, Long>
