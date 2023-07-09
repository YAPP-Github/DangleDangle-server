package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import yapp.be.storage.jpa.volunteerevent.model.QVolunteerEventEntity.volunteerEventEntity
import yapp.be.storage.jpa.volunteerevent.model.VolunteerEventEntity
import java.time.YearMonth

@Component
class VolunteerEventJpaRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventJpaRepositoryCustom {

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
