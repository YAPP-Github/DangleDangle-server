package yapp.be.apiapplication.shelter.scheduler

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import yapp.be.domain.volunteerActivity.model.event.ShelterVolunteerActivityRemindEvent
import yapp.be.domain.volunteerActivity.port.inbound.EditVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.service.WithdrawVolunteerActivityDomainService
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

@Component
class ShelterVolunteerActivityScheduler(
    private val eventPublisher: ApplicationEventPublisher,
    private val editVolunteerActivityUseCase: EditVolunteerActivityUseCase,
    private val getVolunteerActivityUseCase: GetVolunteerActivityUseCase,
    private val withdrawVolunteerActivityUseCase: WithdrawVolunteerActivityDomainService,
) {

    @Scheduled(cron = "0 30 0 * * *")
    fun updateVolunteerEventDone() {
        val volunteerEventIds = getVolunteerActivityUseCase.getVolunteerActivityDone()
        volunteerEventIds.map {
            editVolunteerActivityUseCase.editVolunteerEventStatus(
                it.id,
                VolunteerActivityStatus.DONE
            )
            withdrawVolunteerActivityUseCase.withdrawWaitingQueue(it.id)
        }
    }

    @Scheduled(cron = "0 0 20 * * *")
    fun remindUpcomingVolunteerEvent() {
        val tomorrowVolunteerActivities = getVolunteerActivityUseCase.getTomorrowVolunteerActivities()
        tomorrowVolunteerActivities.forEach { volunteerActivity ->
            eventPublisher.publishEvent(
                ShelterVolunteerActivityRemindEvent(
                    shelterId = volunteerActivity.shelterId,
                    volunteerActivityId = volunteerActivity.id
                )
            )
        }
    }
}
