package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import java.time.LocalDateTime
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithMyParticipationStatusProjection

interface VolunteerEventJpaRepositoryCustom {
    fun findWithParticipationStatusByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerEventWithMyParticipationStatusProjection?

    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerEventEntity>
}
