package yapp.be.storage.jpa.volunteerActivity.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteer.model.QVolunteerEntity.volunteerEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityEntity.volunteerActivityEntity
import yapp.be.storage.jpa.volunteerActivity.model.QVolunteerActivityWaitingQueueEntity.volunteerActivityWaitingQueueEntity
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.QVolunteerActivityWithShelterInfoProjection
import yapp.be.storage.jpa.volunteerActivity.repository.querydsl.model.VolunteerActivityWithShelterInfoProjection

@Component
class VolunteerActivityWaitingQueueJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerActivityWaitingQueueJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findAllWaitParticipantsByVolunteerActivityId(volunteerEventId: Long): List<VolunteerEntity> {
        return queryFactory
            .select(volunteerEntity)
            .from(volunteerEntity)
            .join(volunteerActivityWaitingQueueEntity)
            .on(volunteerActivityWaitingQueueEntity.volunteerId.eq(volunteerEntity.id))
            .where(volunteerActivityWaitingQueueEntity.volunteerActivityId.eq(volunteerEventId))
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllWaitingVolunteerActivityByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerActivityWithShelterInfoProjection> {
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
            .from(
                volunteerActivityEntity
            ).join(
                volunteerActivityWaitingQueueEntity
            ).on(
                volunteerActivityWaitingQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerActivityWaitingQueueEntity.volunteerActivityId.eq(volunteerActivityEntity.id))
            ).join(shelterEntity)
            .on(volunteerActivityEntity.shelterId.eq(shelterEntity.id))
            .orderBy(volunteerActivityEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }
}
