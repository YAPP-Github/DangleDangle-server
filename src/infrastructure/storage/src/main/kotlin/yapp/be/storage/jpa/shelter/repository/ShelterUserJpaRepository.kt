package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.shelter.model.ShelterUserEntity

@Repository
interface ShelterUserJpaRepository : JpaRepository<ShelterUserEntity, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): ShelterUserEntity?
}