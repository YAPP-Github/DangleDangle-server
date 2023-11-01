package yapp.be.apiapplication.volunteer.scheduler

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import yapp.be.domain.volunteerActivity.model.event.VolunteerVolunteerActivityRemindEvent
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase

@Component
class VolutneerVolunteerActivityScheduler(
    private val eventPublisher: ApplicationEventPublisher,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase,
) {
    @Scheduled(fixedDelay = 60000L)
    // @Scheduled(cron = "0 0 20 * * *")
    fun remindUpcomingVolunteerEvent() {
        val tomorrowVolunteerActivities = getVolunteerActivityUseCase.getTomorrowVolunteerActivities()
        tomorrowVolunteerActivities.forEach { volunteerActivity ->
            eventPublisher.publishEvent(
                VolunteerVolunteerActivityRemindEvent(
                    shelterId = volunteerActivity.shelterId,
                    volunteerActivityId = volunteerActivity.id
                )
            )
        }
    }
}
