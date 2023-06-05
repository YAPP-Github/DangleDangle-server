package yapp.be.storage.jpa.shelter.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import yapp.be.storage.jpa.shelter.model.QShelterEntity
import yapp.be.storage.jpa.shelter.model.ShelterEntity

@Repository
class ShelterQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ShelterQueryDslRepository {
    override fun findWithId(id: Long): ShelterEntity? {
        val entity = queryFactory
            .selectFrom(QShelterEntity.shelterEntity)
            .where(QShelterEntity.shelterEntity.id.eq(1L))
            .fetchOne()

        return entity
    }
}
