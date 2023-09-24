package yapp.be.storage.jpa.volunteerActivity.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.VolunteerActivityJpaRepositoryCustom
import java.time.LocalDateTime

interface VolunteerActivityJpaRepository : JpaRepository<VolunteerActivityEntity, Long>, VolunteerActivityJpaRepositoryCustom {
    fun findAllByShelterIdAndDeletedIsFalse(shelterId: Long): List<VolunteerActivityEntity>

    fun findAllByShelterIdAndStatusAndDeletedIsFalse(shelterId: Long, status: VolunteerActivityStatus, pageable: Pageable): Page<VolunteerActivityEntity>
    fun findAllByShelterId(shelterId: Long): List<VolunteerActivityEntity>
    fun findAllByShelterIdAndDeletedIsFalse(shelterId: Long, pageable: Pageable): Page<VolunteerActivityEntity>
    fun findByIdAndShelterIdAndDeletedIsFalse(id: Long, shelterId: Long): VolunteerActivityEntity?
    fun findByEndAtBeforeAndStatusNot(today: LocalDateTime, status: VolunteerActivityStatus): List<VolunteerActivityEntity>
}
