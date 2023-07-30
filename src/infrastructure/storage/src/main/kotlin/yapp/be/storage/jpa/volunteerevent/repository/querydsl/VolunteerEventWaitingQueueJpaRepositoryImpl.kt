package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.volunteer.model.QVolunteerEntity.volunteerEntity
import yapp.be.storage.jpa.volunteer.model.VolunteerEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventWaitingQueueEntity.volunteerEventWaitingQueueEntity
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.QVolunteerEventWithShelterInfoProjection
import yapp.be.storage.jpa.volunteerevent.repository.querydsl.model.VolunteerEventWithShelterInfoProjection

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

    @Transactional(readOnly = true)
    override fun findAllWaitingVolunteerEventByVolunteerId(volunteerId: Long, pageable: Pageable): Page<VolunteerEventWithShelterInfoProjection> {
        val content = queryFactory
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
            .from(
                volunteerEventEntity
            ).join(
                volunteerEventWaitingQueueEntity
            ).on(
                volunteerEventWaitingQueueEntity.volunteerId.eq(volunteerId)
                    .and(volunteerEventWaitingQueueEntity.volunteerEventId.eq(volunteerEventEntity.id))
            ).join(shelterEntity)
            .on(volunteerEventEntity.shelterId.eq(shelterEntity.id))
            .orderBy(volunteerEventEntity.startAt.desc())
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(content, pageable, content.size.toLong())
    }
}
