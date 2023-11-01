package yapp.be.apiapplication.volunteer.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import yapp.be.domain.common.model.AlimtalkMessageTemplate
import yapp.be.domain.common.port.KakaoNotificationHandler
import yapp.be.domain.volunteer.port.inbound.GetVolunteerUseCase
import yapp.be.domain.volunteerActivity.model.event.VolunteerActivityDeletedEvent
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase

@Component
class VolunteerActivityDeletedEventConsumer(
    private val kakaoNotificationHandler: KakaoNotificationHandler,
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase
) {

    private val logger = KotlinLogging.logger { }
    @Async
    @TransactionalEventListener
    fun sendAlimtalkToVolunteer(event: VolunteerActivityDeletedEvent) {
        logger.info { "[봉사삭제이벤트][알림톡 발신][시작]" }
        val volunteerActivityDetail = getVolunteerActivityUseCase.getShelterUserDetailDeletedVolunteerActivityInfo(
            shelterId = event.shelterId,
            volunteerActivityId = event.volunteerActivityId,
        )

        val ids = buildList {
            addAll(volunteerActivityDetail.joiningVolunteers.map { it.id })
            addAll(volunteerActivityDetail.waitingVolunteers.map { it.id })
        }

        val users = getVolunteerUseCase.getAllByIds(ids)

        val variables =
            buildMap {
                users.map { user ->
                    put(
                        user.phone,
                        buildMap {
                            put("name", user.nickname)
                            put("event", volunteerActivityDetail.title)
                            put("date", volunteerActivityDetail.startAt.toLocalDate().toString())
                            put("time", volunteerActivityDetail.startAt.toLocalTime().toString())
                        }
                    )
                }
            }

        kakaoNotificationHandler.request(
            variables = variables,
            template = AlimtalkMessageTemplate.VOLUNTEER_DELETE_EVENT
        )
        logger.info { "[봉사삭제이벤트][알림톡 발신][종료]" }
    }
}
