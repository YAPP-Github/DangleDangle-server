package yapp.be.storage.jpa.volunteerevent.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.VolunteerEventJpaRepositoryCustom

interface VolunteerEventJpaRepository : JpaRepository<VolunteerEventEntity, Long>, VolunteerEventJpaRepositoryCustom
