package yapp.be.storage.jpa.shelter.repository.querydsl

import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.repository.querydsl.model.ShelterWithBookMarkProjection

interface ShelterJpaRepositoryCustom {
    fun findWithBookMarkByIdAndVolunteerId(id: Long, volunteerId: Long): ShelterWithBookMarkProjection?
    fun findAllBookMarkedShelterByVolunteerId(volunteerId: Long): List<ShelterEntity>
    fun findAllBookMarkedShelterByVolunteerIdAndAddress(address: String, volunteerId: Long): List<ShelterEntity>
    fun findAllByAddress(address: String): List<ShelterEntity>
}
