package yapp.be.storage.jpa.event.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.event.model.ParticipateVolunteerEventEventEntity

interface ParticipateVolunteerEventEventJpaRepository : JpaRepository<ParticipateVolunteerEventEventEntity, Long>
