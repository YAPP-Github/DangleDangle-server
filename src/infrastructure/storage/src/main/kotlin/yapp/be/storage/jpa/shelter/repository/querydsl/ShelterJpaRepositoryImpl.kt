package yapp.be.storage.jpa.shelter.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import yapp.be.storage.jpa.shelter.repository.querydsl.model.ShelterWithBookMarkProjection
import yapp.be.storage.jpa.shelter.model.QShelterEntity.shelterEntity
import yapp.be.storage.jpa.shelter.model.QShelterBookMarkEntity.shelterBookMarkEntity
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
}
