package yapp.be.storage.jpa.shelter.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.repository.querydsl.ShelterJpaRepositoryCustom

interface ShelterJpaRepository : JpaRepository<ShelterEntity, Long>, ShelterJpaRepositoryCustom {
    fun findByName(name: String): ShelterEntity?
}
