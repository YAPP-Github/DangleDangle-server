package yapp.be.storage.jpa.shelter.repository.querydsl

import yapp.be.storage.jpa.shelter.repository.querydsl.model.ShelterWithBookMarkProjection

interface ShelterJpaRepositoryCustom {
    fun findWithBookMarkByIdAndVolunteerId(id: Long, volunteerId: Long): ShelterWithBookMarkProjection?
}
