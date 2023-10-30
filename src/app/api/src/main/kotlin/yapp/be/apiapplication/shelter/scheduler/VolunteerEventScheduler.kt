package yapp.be.apiapplication.shelter.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import yapp.be.domain.volunteerActivity.service.EditVolunteerActivityDomainService
import yapp.be.domain.volunteerActivity.service.GetVolunteerActivityDomainService
import yapp.be.domain.volunteerActivity.service.WithdrawVolunteerActivityDomainService
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

@Component
class VolunteerEventScheduler(
    private val editVolunteerActivityDomainService: EditVolunteerActivityDomainService,
    private val getVolunteerActivityDomainService: GetVolunteerActivityDomainService,
    private val withdrawVolunteerActivityDomainService: WithdrawVolunteerActivityDomainService,
) {

    @Scheduled(cron = "0 30 0 * * *")
    fun updateVolunteerEventDone() {
        val volunteerEventIds = getVolunteerActivityDomainService.getVolunteerActivityDone()
        volunteerEventIds.map {
            editVolunteerActivityDomainService.editVolunteerEventStatus(
                it.id,
                VolunteerActivityStatus.DONE
            )
            withdrawVolunteerActivityDomainService.withdrawWaitingQueue(it.id)
        }
    }
}
