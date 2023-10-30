package yapp.be.storage.jpa.volunteerActivity.repository.querydsl

import com.querydsl.core.BooleanBuilder
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
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityEntity.volunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityJoiningQueueEntity.volunteerActivityJoiningQueueEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityWaitingQueueEntity.volunteerActivityWaitingQueueEntity
import yapp.be.storage.jpa.volunteerActivity.model.VolunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.QVolunteerActivityWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.QVolunteerActivityWithShelterInfoProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoAndMyParticipationStatusProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoProjection

@Component
class VolunteerActivityJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerActivityJpaRepositoryCustom {
    @Transactional(readOnly = true)
    override fun findUpcomingVolunteerEventByVolunteerId(volunteerId: Long): VolunteerActivityWithShelterInfoProjection? {
        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            )
            .from(volunteerActivityJoiningQueueEntity)
            .join(volunteerActivityEntity)
            .on(
                volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityEntity.id.eq(volunteerActivityJoiningQueueEntity.volunteerActivityId))
                    .and(volunteerActivityEntity.status.stringValue().eq(VolunteerActivityStatus.IN_PROGRESS.name))
            )
            .join(shelterEntity).on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(volunteerActivityEntity.startAt.after(LocalDateTime.now()))
            .orderBy(volunteerActivityEntity.startAt.asc())
            .fetchFirst()
    }

    @Transactional(readOnly = true)
    override fun findAllByVolunteerId(volunteerId: Long): List<VolunteerActivityEntity> {
        return queryFactory
            .selectFrom(volunteerActivityEntity)
            .join(volunteerActivityJoiningQueueEntity)
            .on(volunteerActivityJoiningQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            .where(volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerId))
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findWithParticipationStatusByIdAndShelterId(id: Long, shelterId: Long): VolunteerActivityWithShelterInfoProjection? {
        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            ).from(volunteerActivityEntity)
            .join(shelterEntity)
            .on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityEntity.id.eq(id)
                    .and(volunteerActivityEntity.shelterId.eq(shelterId))
                    .and(volunteerActivityEntity.deleted.isFalse)
            )
            .fetchOne()
    }

    override fun findWithParticipationStatusByIdAndShelterIdAndDeletedIsTrue(id: Long, shelterId: Long): VolunteerActivityWithShelterInfoProjection? {
        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            ).from(volunteerActivityEntity)
            .join(shelterEntity)
            .on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityEntity.id.eq(id)
                    .and(volunteerActivityEntity.shelterId.eq(shelterId))
            )
            .fetchOne()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonthAndCategory(shelterId: Long, from: LocalDateTime, to: LocalDateTime, category: VolunteerActivityCategory?): List<VolunteerActivityWithShelterInfoProjection> {
        val builder = BooleanBuilder()
        category?.let { builder.and(volunteerActivityEntity.category.eq(it)) }

        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            )
            .from(volunteerActivityEntity)
            .join(shelterEntity).on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            from = from,
                            to = to,
                        )
                    ).and(
                        volunteerActivityEntity.deleted.isFalse
                    ).and(
                        volunteerActivityEntity.status.eq(VolunteerActivityStatus.IN_PROGRESS)
                    ).and(
                        builder
                    )
            ).fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonthAndCategoryAndStatus(shelterId: Long, from: LocalDateTime, to: LocalDateTime, category: List<VolunteerActivityCategory>?, status: VolunteerActivityStatus?): List<VolunteerActivityWithShelterInfoProjection> {
        val builder = BooleanBuilder()
        category?.forEach { builder.or(volunteerActivityEntity.category.eq(it)) }
        status?.let { builder.and(volunteerActivityEntity.status.eq(it)) }

        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            )
            .from(volunteerActivityEntity)
            .join(shelterEntity).on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            from = from,
                            to = to,
                        )
                    ).and(
                        volunteerActivityEntity.deleted.isFalse
                    ).and(
                        builder
                    )
            ).fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllByShelterIdAndYearAndMonth(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<VolunteerActivityWithShelterInfoProjection> {
        return queryFactory
            .select(
                QVolunteerActivityWithShelterInfoProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            )
            .from(volunteerActivityEntity)
            .join(shelterEntity).on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityEntity.shelterId.eq(shelterId)
                    .and(
                        isEventAtBetweenYearAndMonth(
                            from = from,
                            to = to,
                        )
                    ).and(
                        volunteerActivityEntity.deleted.isFalse
                    )
            ).fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoAndMyParticipationStatusProjection> {
        val content = queryFactory
            .select(
                QVolunteerActivityWithShelterInfoAndMyParticipationStatusProjection(
                    volunteerActivityEntity.id,
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.profileImageUrl,
                    volunteerActivityEntity.title,
                    volunteerActivityEntity.recruitNum,
                    shelterEntity.address,
                    volunteerActivityEntity.description,
                    volunteerActivityEntity.ageLimit,
                    volunteerActivityEntity.category,
                    volunteerActivityEntity.status,
                    CaseBuilder()
                        .`when`(volunteerActivityJoiningQueueEntity.isNotNull())
                        .then(
                            CaseBuilder()
                                .`when`(volunteerActivityEntity.status.eq(VolunteerActivityStatus.IN_PROGRESS))
                                .then(UserEventParticipationStatus.JOINING.name)
                                .`when`(volunteerActivityEntity.status.eq(VolunteerActivityStatus.CLOSED))
                                .then(UserEventParticipationStatus.NONE.name)
                                .otherwise(
                                    Expressions.constant(UserEventParticipationStatus.DONE.name)
                                )
                        )
                        .`when`(volunteerActivityWaitingQueueEntity.isNotNull())
                        .then(UserEventParticipationStatus.WAITING.name)
                        .otherwise(
                            Expressions.constant(UserEventParticipationStatus.NONE.name)
                        ),

                    volunteerActivityEntity.startAt,
                    volunteerActivityEntity.endAt
                )
            )
            .from(volunteerActivityEntity)
            .leftJoin(volunteerActivityJoiningQueueEntity)
            .on(
                volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityJoiningQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            )
            .leftJoin(volunteerActivityWaitingQueueEntity)
            .on(
                volunteerActivityWaitingQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityWaitingQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            )
            .join(shelterEntity).on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .where(
                volunteerActivityJoiningQueueEntity.volunteerId.isNotNull()
                    .or(volunteerActivityWaitingQueueEntity.volunteerId.isNotNull())
            )
            .orderBy(volunteerActivityEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }
}

private fun isEventAtBetweenYearAndMonth(from: LocalDateTime, to: LocalDateTime): BooleanExpression {
    return volunteerActivityEntity.startAt.between(from, to)
}
