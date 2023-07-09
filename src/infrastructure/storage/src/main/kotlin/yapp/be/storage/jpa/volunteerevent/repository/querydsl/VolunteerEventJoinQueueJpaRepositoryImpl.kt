package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.volunteer.model.QVolunteerEntity.volunteerEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventJoinQueueEntity.volunteerEventJoinQueueEntity

@Component
class VolunteerEventJoinQueueJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJoinQueueJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findAllJoinParticipantsByVolunteerEventId(volunteerEventId: Long): List<VolunteerEntity> {
        return queryFactory
            .select(volunteerEntity)
            .from(volunteerEventJoinQueueEntity)
            .join(volunteerEntity)
            .on(volunteerEventJoinQueueEntity.volunteerId.eq(volunteerEntity.id))
            .where(volunteerEventJoinQueueEntity.volunteerEventId.eq(volunteerEventId))
            .fetch()
    }
}
