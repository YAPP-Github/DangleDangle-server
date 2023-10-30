package yapp.be.storage.jpa.volunteerActivity.repository.querydsl

import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoProjection

interface VolunteerActivityJpaRepositoryCustom {

    fun findUpcomingVolunteerEventByVolunteerId(volunteerId: Long): VolunteerActivityWithShelterInfoProjection?

    fun findAllByVolunteerId(volunteerId: Long): List<VolunteerActivityEntity>
    fun findWithParticipationStatusByIdAndShelterId(
        id: Long,
        shelterId: Long
    ): VolunteerActivityWithShelterInfoProjection?

    fun findWithParticipationStatusByIdAndShelterIdAndDeletedIsTrue(
        id: Long,
        shelterId: Long
    ): VolunteerActivityWithShelterInfoProjection?

    fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerActivityWithShelterInfoProjection>

    fun findAllByShelterIdAndYearAndMonthAndCategory(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: VolunteerActivityCategory?,
    ): List<VolunteerActivityWithShelterInfoProjection>

    fun findAllByShelterIdAndYearAndMonthAndCategoryAndStatus(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime,
        category: List<VolunteerActivityCategory>?,
        status: VolunteerActivityStatus?,
    ): List<VolunteerActivityWithShelterInfoProjection>

    fun findAllVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoAndMyParticipationStatusProjection>
}
