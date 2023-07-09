package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.volunteer.model.QVolunteerEntity.volunteerEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventWaitingQueueEntity.volunteerEventWaitingQueueEntity

@Component
class VolunteerEventWaitingQueueJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventWaitingQueueJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findAllWaitParticipantsByVolunteerEventId(volunteerEventId: Long): List<VolunteerEntity> {
        return queryFactory
            .select(volunteerEntity)
            .from(volunteerEntity)
            .join(volunteerEventWaitingQueueEntity)
            .on(volunteerEventWaitingQueueEntity.volunteerId.eq(volunteerEntity.id))
            .where(volunteerEventWaitingQueueEntity.volunteerEventId.eq(volunteerEventId))
            .fetch()
    }
}
