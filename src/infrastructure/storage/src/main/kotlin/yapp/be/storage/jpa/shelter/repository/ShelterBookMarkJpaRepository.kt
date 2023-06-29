package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.shelter.model.ShelterBookMarkEntity

@Repository
interface ShelterBookMarkJpaRepository : JpaRepository<ShelterBookMarkEntity, Long> {
    fun findByShelterIdAndVolunteerId(shelterId: Long, volunteerId: Long): ShelterBookMarkEntity?
}
