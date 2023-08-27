package yapp.be.apiapplication.shelter.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import yapp.be.domain.service.EditVolunteerEventDomainService
import yapp.be.domain.service.GetVolunteerEventDomainService
import yapp.be.domain.service.WithdrawVolunteerEventDomainService
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

@Component
class VolunteerEventScheduler(
    private val editVolunteerEventDomainService: EditVolunteerEventDomainService,
    private val getVolunteerEventDomainService: GetVolunteerEventDomainService,
    private val withdrawVolunteerEventDomainService: WithdrawVolunteerEventDomainService,
) {

    @Scheduled(cron = "0 30 0 * * *")
    fun updateVolunteerEventDone() {
        val volunteerEventIds = getVolunteerEventDomainService.getVolunteerEventDone()
        volunteerEventIds.map {
            editVolunteerEventDomainService.editVolunteerEventStatus(
                it.id,
                VolunteerEventStatus.DONE
            )
            withdrawVolunteerEventDomainService.withdrawWaitingQueue(it.id)
        }
    }
}
