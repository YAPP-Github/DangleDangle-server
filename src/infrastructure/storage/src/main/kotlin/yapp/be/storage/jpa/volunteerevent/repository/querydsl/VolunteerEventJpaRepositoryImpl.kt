package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QReminderVolunteerEventProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.ReminderVolunteerEventProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithMyParticipationStatusProjection
import java.time.LocalDate

@Component
class VolunteerEventJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findWithParticipationStatusByIdAndShelterId(id: Long, shelterId: Long): VolunteerEventWithMyParticipationStatusProjection? {
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
                    volunteerEventEntity.startAt,
                    volunteerEventEntity.endAt
                )
            ).from(volunteerEventEntity)
            .join(shelterEntity)
            .on(volunteerEventEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerEventEntity.id.eq(id)
                    .and(volunteerEventEntity.shelterId.eq(shelterId))
                    .and(volunteerEventEntity.deleted.isFalse)
            )
            .fetchOne()
    }

    @Transactional(readOnly = true)
    override fun findWithDayBefore(date: LocalDate): List<ReminderVolunteerEventProjection> {
        return queryFactory
            .select(
                QReminderVolunteerEventProjection(
                    volunteerEventEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    volunteerEventEntity.title,
                    volunteerEventEntity.startAt,
                    volunteerEventEntity.endAt
                )
            ).from(volunteerEventEntity)
            .join(shelterEntity)
            .on(volunteerEventEntity.shelterId.eq(shelterEntity.id))
            .where(
                isEventAfterOneDay(
                    date = date,
                )
                    .and(
                        volunteerEventEntity.deleted.isFalse
                    )
            )
            .fetch()
            .toList()
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

    private fun isEventAfterOneDay(date: LocalDate): BooleanExpression {
        return volunteerEventEntity.startAt.between(date.plusDays(1).atStartOfDay(), date.plusDays(1).atTime(23, 59, 59))
    }
}
