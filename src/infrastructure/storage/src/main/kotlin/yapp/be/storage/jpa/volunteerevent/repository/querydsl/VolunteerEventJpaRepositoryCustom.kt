package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity

interface VolunteerEventJpaRepositoryCustom {

    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        year: Int,
        month: Int,
    ): List<VolunteerEventEntity>
}
