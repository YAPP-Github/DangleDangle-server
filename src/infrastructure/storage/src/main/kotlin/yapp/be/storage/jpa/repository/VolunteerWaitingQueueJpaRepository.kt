package yapp.be.storage.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.model.VolunteerWaitingQueue

@Repository
interface VolunteerWaitingQueueJpaRepository : JpaRepository<VolunteerWaitingQueue, Long>
