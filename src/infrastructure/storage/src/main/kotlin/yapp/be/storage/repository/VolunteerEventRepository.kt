package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinHistoryJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
class VolunteerEventRepository(
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventJoinHistoryJpaRepository: VolunteerEventJoinHistoryJpaRepository,
) : VolunteerEventQueryHandler {
    override fun findAllByShelterId(shelterId: Long): List<SimpleVolunteerEventInfo> {
    }

    override fun findAllWithVolunteerParticipationStatusByShelterIdAndVolunteerId(
        shelterId: Long,
        volunteerId: Long
    ): List<SimpleVolunteerEventInfo> {
        TODO("Not yet implemented")
    }
}
