package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventJoinQueueEntity.volunteerEventJoinQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventWaitingQueueEntity.volunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithShelterInfoProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoProjection

@Component
class VolunteerEventJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findAllByVolunteerId(volunteerId: Long): List<VolunteerEventEntity> {
        return queryFactory
            .selectFrom(volunteerEventEntity)
            .join(volunteerEventJoinQueueEntity)
            .on(volunteerEventJoinQueueEntity.volunteerEventId.eq(volunteerEventEntity.id))
            .where(volunteerEventJoinQueueEntity.volunteerId.eq(volunteerId))
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findWithParticipationStatusByIdAndShelterId(id: Long, shelterId: Long): VolunteerEventWithShelterInfoProjection? {
        return queryFactory
            .select(
                QVolunteerEventWithShelterInfoProjection(
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

    override fun findAllByShelterIdAndYearAndMonthAndStatusAndCategory(shelterId: Long, from: LocalDateTime, to: LocalDateTime, status: VolunteerEventStatus, category: VolunteerEventCategory): List<VolunteerEventWithShelterInfoProjection> {
        return queryFactory
            .select(
                QVolunteerEventWithShelterInfoProjection(
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
            )
            .from(volunteerEventEntity)
            .join(shelterEntity).on(volunteerEventEntity.shelterId.eq(shelterId))
            .where(
                volunteerEventEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            from = from,
                            to = to,
                        )
                    ).and(
                        volunteerEventEntity.deleted.isFalse
                    ).and(
                        volunteerEventEntity.status.eq(status)
                    ).and(
                        volunteerEventEntity.category.eq(category)
                    )
            ).fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonth(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<VolunteerEventWithShelterInfoProjection> {
        return queryFactory
            .select(
                QVolunteerEventWithShelterInfoProjection(
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
            )
            .from(volunteerEventEntity)
            .join(shelterEntity).on(volunteerEventEntity.shelterId.eq(shelterId))
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

    @Transactional(readOnly = true)
    override fun findAllVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerEventWithShelterInfoAndMyParticipationStatusProjection> {
        val content = queryFactory
            .select(
                QVolunteerEventWithShelterInfoAndMyParticipationStatusProjection(
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
                    CaseBuilder()
                        .`when`(volunteerEventJoinQueueEntity.isNotNull())
                        .then(
                            CaseBuilder()
                                .`when`(volunteerEventEntity.status.eq(VolunteerEventStatus.IN_PROGRESS))
                                .then(UserEventParticipationStatus.JOINING.name)
                                .`when`(volunteerEventEntity.status.eq(VolunteerEventStatus.CLOSED))
                                .then(UserEventParticipationStatus.NONE.name)
                                .otherwise(
                                    Expressions.constant(UserEventParticipationStatus.DONE.name)
                                )
                        )
                        .`when`(volunteerEventWaitingQueueEntity.isNotNull())
                        .then(UserEventParticipationStatus.WAITING.name)
                        .otherwise(
                            Expressions.constant(UserEventParticipationStatus.NONE.name)
                        ),

                    volunteerEventEntity.startAt,
                    volunteerEventEntity.endAt
                )
            )
            .from(volunteerEventEntity)
            .leftJoin(volunteerEventJoinQueueEntity)
            .on(
                volunteerEventJoinQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerEventJoinQueueEntity.volunteerEventId.eq(volunteerEventEntity.id))
            )
            .leftJoin(volunteerEventWaitingQueueEntity)
            .on(
                volunteerEventWaitingQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerEventWaitingQueueEntity.volunteerEventId.eq(volunteerEventEntity.id))
            )
            .join(shelterEntity).on(volunteerEventEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerEventJoinQueueEntity.volunteerId.isNotNull()
                    .or(volunteerEventWaitingQueueEntity.volunteerId.isNotNull())
            )
            .orderBy(volunteerEventEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }
}

private fun isEventAtBetweenYearAndMonth(from: LocalDateTime, to: LocalDateTime): BooleanExpression {
    return volunteerEventEntity.startAt.between(from, to)
}
