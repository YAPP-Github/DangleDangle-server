package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.repository.querydsl.ShelterQueryDslRepository

@Repository
interface ShelterJpaRepository : JpaRepository<ShelterEntity, Long>, ShelterQueryDslRepository {
    fun findByName(name: String): ShelterEntity?
}
