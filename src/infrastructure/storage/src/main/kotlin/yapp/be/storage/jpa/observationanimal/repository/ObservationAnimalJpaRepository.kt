package yapp.be.storage.jpa.observationanimal.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.domain.model.ObservationAnimal
import yapp.be.storage.jpa.observationanimal.model.ObservationAnimalEntity

interface ObservationAnimalJpaRepository : JpaRepository<ObservationAnimalEntity, Long> {
    fun findAllByShelterId(shelterId: Long): List<ObservationAnimalEntity>
    fun findByIdAndShelterId(id: Long, shelterId: Long): ObservationAnimal?
}
