package yapp.be.storage.jpa.volunteerActivity.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteer.model.QVolunteerEntity.volunteerEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityEntity.volunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityJoiningQueueEntity.volunteerActivityJoiningQueueEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.QVolunteerActivityWithShelterInfoProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoProjection

@Component
class VolunteerActivityJoiningQueueJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerActivityJoiningQueueJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findAllJoinParticipantsByVolunteerActivityId(volunteerActivityId: Long): List<VolunteerEntity> {
        return queryFactory
            .select(volunteerEntity)
            .from(volunteerActivityJoiningQueueEntity)
            .join(volunteerEntity)
            .on(volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerEntity.id))
            .where(volunteerActivityJoiningQueueEntity.volunteerActivityId.eq(volunteerActivityId))
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllJoinVolunteerActivityByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoProjection> {
        val content = queryFactory
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
            .join(
                volunteerActivityJoiningQueueEntity
            ).on(
                volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityJoiningQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            ).join(shelterEntity)
            .on(
                volunteerActivityEntity.shelterId.eq(shelterEntity.id)
            )
            .where(volunteerActivityEntity.status.eq(VolunteerActivityStatus.IN_PROGRESS))
            .orderBy(volunteerActivityEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }

    override fun findAllDoneVolunteerActivityByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoProjection> {
        val content = queryFactory
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
            .join(
                volunteerActivityJoiningQueueEntity
            ).on(
                volunteerActivityJoiningQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityJoiningQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            ).join(shelterEntity)
            .on(
                volunteerActivityEntity.shelterId.eq(shelterEntity.id)
            )
            .where(volunteerActivityEntity.status.eq(VolunteerActivityStatus.DONE))
            .orderBy(volunteerActivityEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }
}
