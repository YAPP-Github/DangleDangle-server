package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoProjection

interface VolunteerEventJpaRepositoryCustom {

    fun findUpcomingVolunteerEventByVolunteerId(volunteerId: Long): VolunteerEventWithShelterInfoProjection?

    fun findAllByVolunteerId(volunteerId: Long): List<VolunteerEventEntity>
    fun findWithParticipationStatusByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerEventWithShelterInfoProjection?

    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerEventWithShelterInfoProjection>

    fun findAllByShelterIdAndYearAndMonthAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerEventCategory?,
    ): List<VolunteerEventWithShelterInfoProjection>

    fun findAllByShelterIdAndYearAndMonthAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: List<VolunteerEventCategory>?,
        status: VolunteerEventStatus?,
    ): List<VolunteerEventWithShelterInfoProjection>

    fun findAllVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerEventWithShelterInfoAndMyParticipationStatusProjection>
}
