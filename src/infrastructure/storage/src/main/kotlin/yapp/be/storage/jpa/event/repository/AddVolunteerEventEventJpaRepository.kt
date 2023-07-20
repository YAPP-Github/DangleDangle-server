package yapp.be.storage.jpa.event.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.event.model.AddVolunteerEventEventEntity

interface AddVolunteerEventEventJpaRepository : JpaRepository<AddVolunteerEventEventEntity, Long> {
}
