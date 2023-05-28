package yapp.be.storage.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.model.VolunteerJoinQueue

@Repository
interface VolunteerJoinQueueJpaRepository : JpaRepository<VolunteerJoinQueue, Long>
