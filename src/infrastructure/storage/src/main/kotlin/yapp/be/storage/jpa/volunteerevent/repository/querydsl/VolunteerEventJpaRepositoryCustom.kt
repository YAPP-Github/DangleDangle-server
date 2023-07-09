package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithMyParticipationStatusProjection

interface VolunteerEventJpaRepositoryCustom {

    fun findByIdAndShelterIdWithMyParticipationStatus(
        id: Long,
        shelterId: Long
    ): VolunteerEventWithMyParticipationStatusProjection?

    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        year: Int,
        month: Int,
    ): List<VolunteerEventEntity>
}
