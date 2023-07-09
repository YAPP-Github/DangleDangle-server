package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.shelter.model.ShelterUserEntity

interface ShelterUserJpaRepository : JpaRepository<ShelterUserEntity, Long> {
    fun findByEmail(email: String): ShelterUserEntity?
}
