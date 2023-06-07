package yapp.be.storage.jpa.shelter.repository.querydsl

import yapp.be.storage.jpa.shelter.model.ShelterEntity

interface ShelterQueryDslRepository {
    fun findWithId(id: Long): ShelterEntity?
}
