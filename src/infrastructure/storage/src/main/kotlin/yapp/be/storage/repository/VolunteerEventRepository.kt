package yapp.be.storage.repository

import org.springframework.stereotype.Component
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerActivityTypeJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventActivityTypeMappingJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinQueueJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventMappingJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
class VolunteerEventRepository(
    private val volunteerActivityTypeJpaRepository: VolunteerActivityTypeJpaRepository,
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventActivityTypeMappingJpaRepository: VolunteerEventActivityTypeMappingJpaRepository,
    private val volunteerEventJoinQueueJpaRepository: VolunteerEventJoinQueueJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventMappingJpaRepository: VolunteerEventMappingJpaRepository,
) : VolunteerEventQueryHandler {
    override fun countAll(): Int {
        return volunteerEventJpaRepository.count().toInt()
    }
}
