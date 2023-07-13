package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventJoinQueueEntity.volunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventWaitingQueueEntity.volunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithMyParticipationStatusProjection

@Component
class VolunteerEventJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findByIdAndShelterIdWithMyParticipationStatus(id: Long, shelterId: Long): VolunteerEventWithMyParticipationStatusProjection? {
        return queryFactory
            .select(
                QVolunteerEventWithMyParticipationStatusProjection(
                    volunteerEventEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerEventEntity.title,
                    volunteerEventEntity.recruitNum,
                    shelterEntity.address,
                    volunteerEventEntity.description,
                    volunteerEventEntity.ageLimit,
                    volunteerEventEntity.category,
                    volunteerEventEntity.status,
                    volunteerEventJoinQueueEntity.isNotNull,
                    volunteerEventWaitingQueueEntity.isNotNull,
                    volunteerEventEntity.startAt,
                    volunteerEventEntity.endAt
                )
            ).from(volunteerEventEntity)
            .join(shelterEntity)
            .on(volunteerEventEntity.shelterId.eq(shelterEntity.id))
            .leftJoin(volunteerEventWaitingQueueEntity)
            .on(volunteerEventEntity.id.eq(volunteerEventWaitingQueueEntity.volunteerEventId))
            .leftJoin(volunteerEventJoinQueueEntity)
            .on(volunteerEventEntity.id.eq(volunteerEventJoinQueueEntity.volunteerEventId))
            .where(
                volunteerEventEntity.id.eq(id)
                    .and(volunteerEventEntity.shelterId.eq(shelterId))
                    .and(volunteerEventEntity.deleted.isFalse)
            )
            .fetchOne()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonth(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<VolunteerEventEntity> {
        return queryFactory
            .selectFrom(volunteerEventEntity)
            .from(volunteerEventEntity)
            .where(
                volunteerEventEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            from = from,
                            to = to,
                        )
                    ).and(
                        volunteerEventEntity.deleted.isFalse
                    )
            ).fetch()
    }

    private fun isEventAtBetweenYearAndMonth(from: LocalDateTime, to: LocalDateTime): BooleanExpression {
        return volunteerEventEntity.startAt.between(from, to)
    }
}
