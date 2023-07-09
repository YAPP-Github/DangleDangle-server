package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.shelter.model.ShelterBookMarkEntity

interface ShelterBookMarkJpaRepository : JpaRepository<ShelterBookMarkEntity, Long> {
    fun findByShelterIdAndVolunteerId(shelterId: Long, volunteerId: Long): ShelterBookMarkEntity?
}
