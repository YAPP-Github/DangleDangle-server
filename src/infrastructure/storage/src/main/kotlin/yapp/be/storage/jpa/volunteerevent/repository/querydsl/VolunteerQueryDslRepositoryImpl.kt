package yapp.be.storage.jpa.volunteerevent.repository.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class VolunteerQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : VolunteerEventQueryDslRepository
