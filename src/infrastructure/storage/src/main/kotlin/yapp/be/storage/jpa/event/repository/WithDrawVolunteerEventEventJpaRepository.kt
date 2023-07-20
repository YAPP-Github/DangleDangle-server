package yapp.be.storage.jpa.event.repository

import org.springframework.data.jpa.repository.JpaRepository
import yapp.be.storage.jpa.event.model.WithDrawVolunteerEventEventEntity

interface WithDrawVolunteerEventEventJpaRepository : JpaRepository<WithDrawVolunteerEventEventEntity, Long> {
}
