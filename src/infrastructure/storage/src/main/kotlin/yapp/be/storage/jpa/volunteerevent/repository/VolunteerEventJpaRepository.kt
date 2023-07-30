package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.VolunteerEventJpaRepositoryCustom

interface VolunteerEventJpaRepository : JpaRepository<VolunteerEventEntity, Long>, VolunteerEventJpaRepositoryCustom {
    fun findAllByShelterId(shelterId: Long): List<VolunteerEventEntity>

    fun findAllByShelterIdAndStatus(shelterId: Long, status: VolunteerEventStatus, pageable: Pageable): Page<VolunteerEventEntity>

    fun findAllByShelterId(shelterId: Long, pageable: Pageable): Page<VolunteerEventEntity>
    fun findByIdAndShelterIdAndDeletedIsFalse(id: Long, shelterId: Long): VolunteerEventEntity?
}
