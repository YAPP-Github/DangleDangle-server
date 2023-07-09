package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventJoinQueueEntity.volunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventWaitingQueueEntity.volunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithMyParticipationStatusProjection
import java.time.YearMonth

@Component
class VolunteerEventJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findByIdAndShelterIdWithMyParticipationStatus(id: Long, shelterId: Long): VolunteerEventWithMyParticipationStatusProjection? {
        return queryFactory
            .select(
                QVolunteerEventWithMyParticipationStatusProjection(
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
            )
            .fetchOne()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonth(shelterId: Long, year: Int, month: Int): List<VolunteerEventEntity> {
        return queryFactory
            .selectFrom(volunteerEventEntity)
            .from(volunteerEventEntity)
            .where(
                volunteerEventEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            year = year,
                            month = month
                        )
                    )
            ).fetch()
    }

    private fun isEventAtBetweenYearAndMonth(year: Int, month: Int): BooleanExpression {
        val date = YearMonth.of(year, month)
        val startOfMonth = date.atDay(1).atStartOfDay()
        val endOfMonth = date.atEndOfMonth().atTime(23, 59, 59)

        return volunteerEventEntity.startAt.between(startOfMonth, endOfMonth)
            .and(volunteerEventEntity.endAt.between(startOfMonth, endOfMonth))
    }
}
