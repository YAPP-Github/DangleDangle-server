package yapp.be.storage.jpa.shelter.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.repository.querydsl.model.ShelterWithBookMarkProjection
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.shelter.model.QShelterBookMarkEntity.shelterBookMarkEntity
import yapp.be.storage.jpa.shelter.model.ShelterEntity
import yapp.be.storage.jpa.shelter.repository.querydsl.model.QShelterWithBookMarkProjection

@Component
class ShelterJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ShelterJpaRepositoryCustom {

    @Transactional(readOnly = true)
    override fun findWithBookMarkByIdAndVolunteerId(id: Long, volunteerId: Long): ShelterWithBookMarkProjection? {
        return queryFactory
            .select(
                QShelterWithBookMarkProjection(
                    shelterEntity.id,
                    shelterEntity.name,
                    shelterEntity.description,
                    shelterEntity.address,
                    shelterEntity.phoneNum,
                    shelterEntity.bankName,
                    shelterEntity.bankAccountNum,
                    shelterEntity.notice,
                    shelterEntity.parkingEnabled,
                    shelterEntity.parkingNotice,
                    shelterEntity.profileImageUrl,
                    shelterBookMarkEntity.isNotNull
                )
            ).from(
                shelterEntity
            ).leftJoin(
                shelterBookMarkEntity
            ).on(
                shelterBookMarkEntity.shelterId.eq(shelterEntity.id)
                    .and(shelterBookMarkEntity.volunteerId.eq(volunteerId))
            )
            .where(
                shelterEntity.id.eq(id)
            ).fetchOne()
    }

    @Transactional(readOnly = true)
    override fun findAllBookMarkedShelterByVolunteerId(volunteerId: Long): List<ShelterEntity> {
        return queryFactory
            .selectFrom(
                shelterEntity
            ).join(shelterBookMarkEntity)
            .on(shelterEntity.id.eq(shelterBookMarkEntity.shelterId))
            .where(shelterBookMarkEntity.volunteerId.eq(volunteerId))
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllBookMarkedShelterByVolunteerIdAndAddress(address: String, volunteerId: Long): List<ShelterEntity> {
        return queryFactory
            .selectFrom(
                shelterEntity
            ).join(shelterBookMarkEntity)
            .on(shelterEntity.id.eq(shelterBookMarkEntity.shelterId))
            .where(
                shelterBookMarkEntity.volunteerId.eq(volunteerId)
                    .and(
                        shelterEntity.address.address.like(address)
                    )
            )
            .fetch()
    }

    @Transactional(readOnly = true)
    override fun findAllByAddress(address: String): List<ShelterEntity> {
        return queryFactory
            .selectFrom(shelterEntity)
            .where(shelterEntity.address.address.contains(address))
            .fetch()
    }
}
