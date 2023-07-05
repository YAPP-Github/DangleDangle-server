package yapp.be.storage.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.enums.volunteerevent.UserEventWaitingStatus
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventJoinQueueJpaRepository
import yapp.be.storage.jpa.volunteerevent.repository.VolunteerEventWaitingQueueJpaRepository

@Component
class VolunteerEventRepository(
    private val volunteerEventJpaRepository: VolunteerEventJpaRepository,
    private val volunteerEventWaitingQueueJpaRepository: VolunteerEventWaitingQueueJpaRepository,
    private val volunteerEventJoinQueueJpaRepository: VolunteerEventJoinQueueJpaRepository,
) : VolunteerEventQueryHandler {

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonth(
        shelterId: Long,
        year: Int,
        month: Int
    ): List<SimpleVolunteerEventInfo> {
        val volunteerEventEntityMap =
            volunteerEventJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                year = year,
                month = month
            ).associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return volunteerEventEntityMap
            .values
            .map {
                val joinQueue = joinQueueEntityMap[it.id]!!
                val waitingQueue = waitingQueueEntityMap[it.id]!!

                SimpleVolunteerEventInfo(
                    volunteerEventId = it.id,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.status,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue.size,
                    waitingNum = waitingQueue.size,
                    myParticipationStatus = UserEventWaitingStatus.NONE,
                )
            }.toList()
    }

    @Transactional(readOnly = true)
    override fun findAllWithMyParticipationStatusByShelterIdAndVolunteerIdAndYearAndMonth(shelterId: Long, volunteerId: Long, year: Int, month: Int): List<SimpleVolunteerEventInfo> {
        val volunteerEventEntityMap =
            volunteerEventJpaRepository.findAllByShelterIdAndYearAndMonth(
                shelterId = shelterId,
                year = year,
                month = month
            ).associateBy { it.id }

        val joinQueueEntityMap =
            volunteerEventJoinQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        val waitingQueueEntityMap =
            volunteerEventWaitingQueueJpaRepository
                .findAllByVolunteerEventIdIn(volunteerEventEntityMap.keys)
                .groupBy { it.volunteerEventId }

        return volunteerEventEntityMap
            .values
            .map {
                val joinQueue = joinQueueEntityMap[it.id]!!
                val waitingQueue = waitingQueueEntityMap[it.id]!!

                SimpleVolunteerEventInfo(
                    volunteerEventId = it.id,
                    title = it.title,
                    category = it.category,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    eventStatus = it.status,
                    recruitNum = it.recruitNum,
                    participantNum = joinQueue.size,
                    waitingNum = waitingQueue.size,
                    myParticipationStatus = getMyParticipationStatus(
                        volunteerId = volunteerId,
                        joinQueue = joinQueue,
                        waitingQueue = waitingQueue
                    ),
                )
            }.toList()
    }

    private fun getMyParticipationStatus(
        volunteerId: Long,
        joinQueue: List<VolunteerEventJoinQueueEntity>,
        waitingQueue: List<VolunteerEventWaitingQueueEntity>
    ): UserEventWaitingStatus {

        return if (joinQueue.any { it.volunteerId == volunteerId })
            UserEventWaitingStatus.JOINING
        else if (waitingQueue.any { it.volunteerId == volunteerId })
            UserEventWaitingStatus.WAITING
        else
            UserEventWaitingStatus.NONE
    }
}
