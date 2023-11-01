package yapp.be.apiapplication.volunteer.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import yapp.be.domain.common.model.AlimtalkMessageTemplate
import yapp.be.domain.common.port.KakaoNotificationHandler
import yapp.be.domain.shelter.port.inbound.shelter.GetShelterUseCase
import yapp.be.domain.volunteer.port.inbound.GetVolunteerUseCase
import yapp.be.domain.volunteerActivity.model.event.ShelterVolunteerActivityRemindEvent
import yapp.be.domain.volunteerActivity.model.event.VolunteerVolunteerActivityRemindEvent
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase

@Component
class VolunteerActivityRemindEventConsumer(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getShelterUseCase: GetShelterUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase,
    private val kakaoNotificationHandler: KakaoNotificationHandler
) {

    private val logger = KotlinLogging.logger { }
    @Async
    @EventListener
    fun sendAlimtalkToShelter(event: ShelterVolunteerActivityRemindEvent) {
        logger.info { "[봉사리마인드이벤트][보호소][알림톡 발신][시작]" }
        val volunteerActivityDetail = getVolunteerActivityUseCase.getShelterUserDetailDeletedVolunteerActivityInfo(
            shelterId = event.shelterId,
            volunteerActivityId = event.volunteerActivityId,
        )

        val shelter = getShelterUseCase.getShelterById(event.shelterId)

        val variables =
            buildMap {
                put(
                    shelter.phoneNumber,
                    buildMap {
                        put("name", shelter.name)
                        put("event", volunteerActivityDetail.title)
                        put("date", volunteerActivityDetail.startAt.toLocalDate().toString())
                        put("time", volunteerActivityDetail.startAt.toLocalTime().toString())
                    }
                )
            }

        kakaoNotificationHandler.request(
            variables = variables,
            template = AlimtalkMessageTemplate.SHELTER_REMIND_EVENT
        )
        logger.info { "[봉사리마인드이벤트][보호소][알림톡 발신][종료]" }
    }

    @Async
    @EventListener
    fun sendAlimtalkToVolunteer(event: VolunteerVolunteerActivityRemindEvent) {
        logger.info { "[봉사리마인드이벤트][봉사자][알림톡 발신][시작]" }
        val volunteerActivityDetail = getVolunteerActivityUseCase.getShelterUserDetailDeletedVolunteerActivityInfo(
            shelterId = event.shelterId,
            volunteerActivityId = event.volunteerActivityId,
        )

        val users = getVolunteerUseCase.getAllByIds(volunteerActivityDetail.joiningVolunteers.map { it.id })

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
        if (variables.isEmpty()) {
            logger.info { "[봉사리마인드이벤트][봉사자][알림톡 발신][생략] (cause=참여자 없음)" }
            return
        }

        kakaoNotificationHandler.request(
            variables = variables,
            template = AlimtalkMessageTemplate.VOLUNTEER_REMIND_EVENT
        )
        logger.info { "[봉사리마인드이벤트][봉사자][알림톡 발신][종료]" }
    }
}
