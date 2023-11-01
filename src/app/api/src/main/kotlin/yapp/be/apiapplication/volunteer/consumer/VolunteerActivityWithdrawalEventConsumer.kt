package yapp.be.apiapplication.volunteer.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import yapp.be.domain.common.model.AlimtalkMessageTemplate
import yapp.be.domain.common.port.KakaoNotificationHandler
import yapp.be.domain.volunteer.port.inbound.GetVolunteerUseCase
import yapp.be.domain.volunteerActivity.model.event.VolunteerActivityWithdrawalEvent
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase

@Component
class VolunteerActivityWithdrawalEventConsumer(
    private val kakaoNotificationHandler: KakaoNotificationHandler,
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase
) {

    private val logger = KotlinLogging.logger { }
    @Async
    @EventListener
    fun sendAlimtalkToVolunteer(event: VolunteerActivityWithdrawalEvent) {
        logger.info { "[봉사철회이벤트][알림톡 발신][시작]" }
        val volunteerActivityDetail = getVolunteerActivityUseCase.getShelterUserDetailDeletedVolunteerActivityInfo(
            shelterId = event.shelterId,
            volunteerActivityId = event.volunteerActivityId,
        )

        if (volunteerActivityDetail.waitingVolunteers.isEmpty()) {
            logger.info { "[봉사철회이벤트][알림톡 발신][생략] (발신 대상 없음)" }
            return
        }

        val users = getVolunteerUseCase.getAllByIds(volunteerActivityDetail.waitingVolunteers.map { it.id })

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
            template = AlimtalkMessageTemplate.WAITING_VOLUNTEER_AVAILABLE_EVENT
        )
        logger.info { "[봉사철회이벤트][알림톡 발신][종료]" }
    }
}
