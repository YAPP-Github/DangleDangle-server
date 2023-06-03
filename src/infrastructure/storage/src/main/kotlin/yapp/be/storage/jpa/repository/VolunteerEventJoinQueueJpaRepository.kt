package yapp.be.storage.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.model.VolunteerEventJoinQueueEntity

@Repository
interface VolunteerEventJoinQueueJpaRepository : JpaRepository<VolunteerEventJoinQueueEntity, Long>
